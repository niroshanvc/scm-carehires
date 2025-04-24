package com.carehires.utils;

import com.carehires.common.GlobalVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConfigurationReader {

    private static final Logger logger = LogManager.getLogger(DataConfigurationReader.class);
    private static final String INCREMENT_VARIABLE = "{{increment}}";
    private static final String AGENCY = "agency";
    private static final String PROVIDER = "provider";
    private static final String UNIQUE_NUMBER = "<uniqueNumber>";
    private static final Map<String, Integer> incrementCache = new HashMap<>();

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

        if (data.contains("<siteCode>")) {
            data = data.replace("<siteCode>", generateSiteCode());
        }

        return data;
    }

    private static String replaceIncrementPlaceholder(String data, String entityType) {
        // Replace specific placeholders for agency and provider
        data = replaceSpecificIncrement(data, "<agencyIncrement>", AGENCY);
        data = replaceSpecificIncrement(data, "<providerIncrement>", PROVIDER);

        // Replace generic increment placeholder "{{increment}}"
        if (data.contains(INCREMENT_VARIABLE)) {
            int uniqueValue = getCurrentIncrementValue(entityType);
            data = data.replace(INCREMENT_VARIABLE, String.valueOf(uniqueValue));
            logger.info("Replaced {{increment}} with {} for entity type: {}", uniqueValue, entityType);
        }
        return data;
    }

    private static String replaceSpecificIncrement(String data, String placeholder, String key) {
        if (data.contains(placeholder)) {
            Integer value = (Integer) GlobalVariables.getVariable(key + "_incrementValue");
            if (value != null) {
                data = data.replace(placeholder, String.valueOf(value));
            } else {
                logger.error("{} increment value not found in GlobalVariables.", key);
            }
        }
        return data;
    }

    private static int generateTimestampBasedIncrementValue() {
        long currentTimeMillis = System.currentTimeMillis();
        return (int) (currentTimeMillis % 1000000); // Limit to 6 digits
    }

    public static synchronized int getCurrentIncrementValue(String entityType) {
        // Check if the increment value for the entity type is already cached
        if (incrementCache.containsKey(entityType)) {
            return incrementCache.get(entityType);
        }

        // Generate a unique value based on the timestamp
        int uniqueValue = generateTimestampBasedIncrementValue();
        incrementCache.put(entityType, uniqueValue); // Cache the value for the entity type
        logger.info("Generated and cached unique increment value for {}: {}", entityType, uniqueValue);
        return uniqueValue;
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

    /**
     * Generates a random siteCode in the format XX-XXXX.
     * The first two characters are uppercase letters, and the last four are numeric.
     *
     * @return A randomly generated siteCode (e.g., AB-1234).
     */
    public static String generateSiteCode() {
        // Generate the first two random uppercase letters
        StringBuilder siteCode = new StringBuilder();
        siteCode.append(generateRandomLetter()); // First letter
        siteCode.append(generateRandomLetter()); // Second letter

        // Add the hyphen
        siteCode.append("-");

        // Generate the last four random digits
        String numericPart = String.format("%04d", random.nextInt(10000));
        siteCode.append(numericPart);

        return siteCode.toString();
    }
}