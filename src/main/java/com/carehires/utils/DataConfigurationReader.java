package com.carehires.utils;

import com.carehires.common.GlobalVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConfigurationReader {

    private static final Logger logger = LogManager.getLogger(DataConfigurationReader.class);
    private static int incrementValue = -1;
    private static final String INCREMENT_FILE_PATH_AGENCY = "src/main/resources/agency_increment_value.txt";
    private static final String INCREMENT_FILE_PATH_PROVIDER = "src/main/resources/provider_increment_value.txt";
    private static final String INCREMENT_FILE_PATH_WORKER = "src/main/resources/worker_increment_value.txt";
    private static final String INCREMENT_FILE_PATH_AGREEMENT = "src/main/resources/agreement_increment_value.txt";
    private static final String INCREMENT_VALUE = "_incrementValue";
    private static final String INCREMENT_VARIABLE = "{{increment}}";
    private static final String AGENCY = "agency";
    private static final String PROVIDER = "provider";
    private static final String UNIQUE_NUMBER = "<uniqueNumber>";

    // Private constructor to prevent instantiation
    private DataConfigurationReader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    // Method to read data from the YAML file
    public static String readDataFromYmlFile(String entityType, String fileName, String... keys) {
        try (FileInputStream fis = new FileInputStream("src/main/resources/" + fileName + ".yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(fis);

            Object value = getValueFromKeys(map, keys);
            if (value == null) {
                logger.error("Key not found in YAML: {}", String.join(" -> ", keys));
                return null;
            }

            String data = value.toString();
            data = replaceDynamicPlaceholders(data, entityType);
            return data;
        } catch (Exception ex) {
            logger.error("Error reading YAML file: {}", ex.getMessage(), ex);
            return null;
        }
    }

    private static Object getValueFromKeys(Map<String, Object> map, String... keys) {
        Object value = map;
        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<String, Object>) value).get(key);
            } else {
                return null;
            }
        }
        return value;
    }

    private static String replaceDynamicPlaceholders(String data, String entityType) {
        if (data.contains(UNIQUE_NUMBER)) {
            data = data.replace(UNIQUE_NUMBER, generateUniqueNumber());
        }

        if (data.contains("<insuranceNumber>")) {
            data = data.replace("<insuranceNumber>", generateNationalInsuranceNumber());
        }

        if (data.contains("<shareCode>")) {
            data = data.replace("<shareCode>", generateShareCodeNumber());
        }

        if (data.contains("<passportNumber>")) {
            data = data.replace("<passportNumber>", generatePassportNumber());
        }

        if (data.contains(INCREMENT_VARIABLE)) {
            data = replaceIncrementPlaceholder(data, entityType);
        }

        if (data.contains("<accountNumber>")) {
            data = data.replace("<accountNumber>", generateAccountNumber(entityType));
        }

        if (data.contains("<costCenterNumber>")) {
            data = data.replace("<costCenterNumber>", generateCostCenterNumber(entityType));
        }

        if (data.matches(".*\\btoday\\s*[+-]\\s*\\d+\\b.*")) {
            data = processDynamicDate(data);
        }

        return data;
    }

    private static String replaceIncrementPlaceholder(String data, String entityType) {
        // Replace specific placeholders for agency and provider
        data = replaceSpecificIncrement(data, "<agencyIncrement>", AGENCY);
        data = replaceSpecificIncrement(data, "<providerIncrement>", PROVIDER);

        // Replace generic increment placeholder "{{increment}}"
        if (data.contains(INCREMENT_VARIABLE)) {
            Integer cachedValue = getOrLoadIncrementValue(entityType);
            if (cachedValue != null) {
                data = data.replace(INCREMENT_VARIABLE, String.valueOf(cachedValue));
                logger.info("Replaced {{increment}} with {} for entity type: {}", cachedValue, entityType);
                // Update the increment value in the file
                String filePath = getFilePathForEntity(entityType);
                if (filePath != null) {
                    updateIncrementValueInFile(filePath, cachedValue + 1);
                    logger.info("Increment value updated to {} in file: {}", cachedValue + 1, filePath);
                } else {
                    logger.error("Unable to update increment value. File path not found for entity type: {}", entityType);
                }
            }
        }
        return data;
    }

    private static String replaceSpecificIncrement(String data, String placeholder, String key) {
        if (data.contains(placeholder)) {
            Integer value = (Integer) GlobalVariables.getVariable(key + INCREMENT_VALUE);
            if (value != null) {
                data = data.replace(placeholder, String.valueOf(value));
            } else {
                logger.error("{} increment value not found in GlobalVariables.", key);
            }
        }
        return data;
    }

    private static Integer getOrLoadIncrementValue(String entityType) {
        Integer cachedValue = (Integer) GlobalVariables.getVariable(entityType + INCREMENT_VALUE);
        if (cachedValue == null) {
            String filePath = getFilePathForEntity(entityType);
            if (filePath == null) {
                logger.error("No increment file path found for entity type: {}", entityType);
                return null;
            }
            cachedValue = readIncrementValueFromFile(filePath);
            if (cachedValue == -1) {
                logger.error("Failed to read increment value from file for entity type: {}", entityType);
                return null;
            }
            GlobalVariables.storeIncrementedValue(entityType, cachedValue);
        }
        return cachedValue;
    }


    public static void updateIncrementValueInFile(String filePath, int newValue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(newValue));
            logger.info("Updated increment value to {} in file: {}", newValue, filePath);
        } catch (IOException e) {
            logger.error("Error updating increment value in file: {}", e.getMessage(), e);
        }
    }

    private static int readIncrementValueFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            char[] buffer = new char[10];
            int numCharsRead = reader.read(buffer);
            String incrementValueStr = new String(buffer, 0, numCharsRead).trim();
            return Integer.parseInt(incrementValueStr);
        } catch (IOException | NumberFormatException e) {
            logger.error("Error reading increment value from file: {}", e.getMessage(), e);
            return -1;
        }
    }

    // Change this method to public so it can be accessed from Hooks and other places
    public static synchronized int getCurrentIncrementValue(String entityType) {
        // First check if we have it in GlobalVariables
        Integer cachedValue = (Integer) GlobalVariables.getVariable(entityType + "_incrementValue");
        if (cachedValue != null) {
            logger.info("Retrieved {} increment value from GlobalVariables: {}", entityType, cachedValue);
            return cachedValue;
        }

        // If not in GlobalVariables, load from file
        String filePath = getFilePathForEntity(entityType);
        if (filePath == null || filePath.isEmpty()) {
            logger.error("File path for entity {} is invalid or not found, defaulting to 1", entityType);
            return 1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                int value = Integer.parseInt(line.trim());
                // Store in GlobalVariables for future use
                GlobalVariables.setVariable(entityType + "_incrementValue", value);
                logger.info("Loaded {} increment value from file and stored in GlobalVariables: {}", entityType, value);
                return value;
            }
        } catch (IOException e) {
            logger.error("Failed to load increment value for {}", entityType, e);
        }
        return 1;
    }

    // Private method to load the increment value from file
    private static int loadIncrementValueFromFile(String entityType) {
        String filePath = getFilePathForEntity(entityType);

        // Check if the file path is null or empty
        if (filePath == null || filePath.isEmpty()) {
            logger.error("File path for entity {} is invalid or not found, defaulting to 1", entityType);
            return 1; // Default value if the file path is invalid
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            logger.error("Failed to load increment value for {}", entityType + ", defaulting to 1", e);
        }
        return 1; // Default value if the file doesn't exist or couldn't be read
    }

    // Save the new increment value
    public static synchronized void storeNewIncrementValue(String entityType) {
        if (incrementValue != -1) {
            saveIncrementValueToFile(entityType, incrementValue);
        }
    }

    // Save the increment value to the file
    private static void saveIncrementValueToFile(String entityType, int value) {
        String filePath = getFilePathForEntity(entityType);
        try {
            assert filePath != null;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(String.valueOf(value));
            }
        } catch (IOException e) {
            logger.error("Failed to save increment value for {}", entityType, e);
        }
    }

    public static String getFilePathForEntity(String entityType) {
        logger.info("********** Received entity type: {}", entityType);
        String lowerCaseEntityType = entityType.toLowerCase();
        if (!Arrays.asList(AGENCY, PROVIDER, "worker", "job", "agreement").contains(lowerCaseEntityType)) {
            throw new IllegalArgumentException("Unknown entity type: " + entityType);
        }
        return switch (lowerCaseEntityType) {
            case AGENCY -> INCREMENT_FILE_PATH_AGENCY;
            case PROVIDER -> INCREMENT_FILE_PATH_PROVIDER;
            case "worker" -> INCREMENT_FILE_PATH_WORKER;
            case "agreement" -> INCREMENT_FILE_PATH_AGREEMENT;
            case "job" -> null; // No increment file for Jobs and Agreements
            default -> throw new IllegalArgumentException("Unknown entity type: " + entityType);
        };
    }

    private static String generateUniqueNumber() {
        long seed = System.nanoTime();
        Random random = new Random(seed);
        return String.format("%010d", random.nextInt(1000000000));
    }

    public static Map<String, String> getUserCredentials(String userType) {
        Map<String, String> credentials = new HashMap<>();
        try (FileInputStream fis = new FileInputStream("src/main/resources/user-credentials.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(fis);
            Map<String, String> userCredentials = (Map<String, String>) map.get(userType);

            credentials.put("username", userCredentials.get("username"));
            credentials.put("password", userCredentials.get("password"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return credentials;
    }

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    /**
     * Generates a unique National Insurance Number in the format XX000000X.
     *
     * @return A unique National Insurance Number (e.g., AB123456C).
     */
    private static String generateNationalInsuranceNumber() {
        // Generate the first two characters
        StringBuilder niNumber = new StringBuilder();
        niNumber.append(generateRandomLetter());  // First letter
        niNumber.append(generateRandomLetter());  // Second letter

        // Generate the 6-digit number part
        String numericPart = String.format("%06d", random.nextInt(1000000));
        niNumber.append(numericPart);

        // Generate the last character
        niNumber.append(generateRandomLetter());

        return niNumber.toString();
    }

    /**
     * Generates a random uppercase letter.
     *
     * @return A random letter from A-Z.
     */
    private static char generateRandomLetter() {
        return LETTERS.charAt(random.nextInt(LETTERS.length()));
    }

    /**
     * Generates a unique Share Code Number in the format X0000000X.
     *
     * @return A unique Share Code Number (e.g., A1234567C).
     */
    private static String generateShareCodeNumber() {
        // Generate the first two characters
        StringBuilder shareCode = new StringBuilder();
        shareCode.append(generateRandomLetter());  // First letter
        shareCode.append(generateRandomLetter());  // Second letter

        // Generate the 6-digit number part
        String numericPart = String.format("%06d", random.nextInt(1000000));
        shareCode.append(numericPart);

        // Generate the last character
        shareCode.append(generateRandomLetter());

        return shareCode.toString();
    }

    /**
     * Generates a unique Passport Number in the format XX0000000.
     *
     * @return A unique Share Code Number (e.g., AB1234567).
     */
    private static String generatePassportNumber() {
        // Generate the first two characters
        StringBuilder passport = new StringBuilder();
        passport.append(generateRandomLetter()); // First letter
        passport.append(generateRandomLetter()); // Second letter

        // Generate the 7-digit number part
        String numericPart = String.format("%07d", random.nextInt(10000000));
        passport.append(numericPart);

        return passport.toString();
    }

    private static String generateAccountNumber(String entityType) {
        int currentValue = getCurrentIncrementValue(entityType);
        return String.format("%08d", currentValue);
    }

    private static String generateCostCenterNumber(String entityType) {
        int incrementValue = getCurrentIncrementValue(entityType); // Auto-increment value
        return String.format("CC%06d", incrementValue); // Format as CCXXXXXX
    }

    // this method is using when user wants to pass the date as today + n number of format from the yml file
    public static String processDynamicDate(String data) {
        Pattern datePattern = Pattern.compile("today\\s*([+-])\\s*(\\d+)");
        Matcher matcher = datePattern.matcher(data);

        while(matcher.find()) {
            String operator = matcher.group(1); // "+" or "-"
            int days = Integer.parseInt(matcher.group(2)); // Number of days

            // Calculate the date
            LocalDate calculatedDate = operator.equals("+") ?
                    LocalDate.now().plusDays(days) : LocalDate.now().minusDays(days);

            String formattedDate = calculatedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
            data = data.replaceFirst(Pattern.quote(matcher.group(0)), formattedDate);
        }

        return data;
    }
}