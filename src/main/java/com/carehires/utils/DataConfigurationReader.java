package com.carehires.utils;

import com.carehires.common.GlobalVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class DataConfigurationReader {

    private static final Logger logger = LogManager.getLogger(DataConfigurationReader.class);
    private static int incrementValue = -1;
    private static final String INCREMENT_FILE_PATH_AGENCY = "src/main/resources/agency_increment_value.txt";
    private static final String INCREMENT_FILE_PATH_PROVIDER = "src/main/resources/provider_increment_value.txt";
    private static final String INCREMENT_FILE_PATH_WORKER = "src/main/resources/worker_increment_value.txt";
    private static final String INCREMENT_VALUE = "_incrementValue";

    // Method to read data from the YAML file
    public static String readDataFromYmlFile(String entityType, String fileName, String... keys) {
        String data = null;
        try (FileInputStream fis = new FileInputStream("src/main/resources/" + fileName + ".yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(fis);

            Object value = map;
            for (String key : keys) {
                if (value instanceof Map) {
                    value = ((Map<String, Object>) value).get(key);
                } else {
                    value = null;
                    break;
                }
            }

            if (value != null) {
                data = value.toString();

                if (data.equals("<uniqueNumber>")) {
                    data = generateUniqueNumber();  // Replace with unique number
                }

                if (data.equals("<insuranceNumber>")) {
                    data = generateNationalInsuranceNumber(); // Replace with national insurance number
                }

                // Replace the {{increment}} placeholder with the current increment value
                if (data.contains("{{increment}}")) {
                    int incrementValue = GlobalVariables.getVariable(entityType + INCREMENT_VALUE, Integer.class);
                    data = data.replace("{{increment}}", String.valueOf(incrementValue));  // Change entityType based on the test
                }

            } else {
                logger.error("Key not found in YAML: {}", String.join(" -> ", keys));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    // Change this method to public so it can be accessed from Hooks and other places
    public static int getCurrentIncrementValue(String entityType) {
        if (incrementValue == -1) {
            incrementValue = loadIncrementValueFromFile(entityType);
        }
        return incrementValue;
    }

    // Private method to load the increment value from file
    private static int loadIncrementValueFromFile(String entityType) {
        String filePath = getFilePathForEntity(entityType);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            logger.error("Failed to load increment value for {}", entityType + ", defaulting to 1", e);
        }
        return 1; // Default value if the file doesn't exist or couldn't be read
    }

    // Save the new increment value
    public static void storeNewIncrementValue(String entityType) {
        incrementValue++; // Increment the value
        saveIncrementValueToFile(entityType, incrementValue); // Save it to a file
    }

    // Save the increment value to the file
    private static void saveIncrementValueToFile(String entityType, int value) {
        String filePath = getFilePathForEntity(entityType);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(value));
        } catch (IOException e) {
            logger.error("Failed to save increment value for {}", entityType, e);
        }
    }

    private static String generateUniqueNumber() {
        long seed = System.nanoTime();
        Random random = new Random(seed);
        return String.format("%010d", random.nextInt(1000000000));
    }

    private static String getFilePathForEntity(String entityType) {
        return switch (entityType.toLowerCase()) {
            case "agency" -> INCREMENT_FILE_PATH_AGENCY;
            case "provider" -> INCREMENT_FILE_PATH_PROVIDER;
            case "worker" -> INCREMENT_FILE_PATH_WORKER;
            default -> throw new IllegalArgumentException("Unknown entity type: " + entityType);
        };
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
    public static String generateNationalInsuranceNumber() {
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
     * Parses the YAML file and counts the number of datasets under a specific section.
     *
     * @param sectionName the name of the section to count datasets for (e.g., "Education and Training").
     * @param ymlFile the name of the YAML file to parse.
     * @return the number of datasets in the section.
     */
    public int countDataSetsInSection(String sectionName, String ymlFile) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ymlFile)) {
            if (inputStream == null) {
                throw new RuntimeException("YAML file not found!");
            }

            Map<String, Object> data = yaml.load(inputStream);
            if (data.containsKey(sectionName)) {
                // The section is represented as a LinkedHashMap
                LinkedHashMap<String, Object> sectionData = (LinkedHashMap<String, Object>) data.get(sectionName);
                return sectionData.size();  // Return the number of datasets
            } else {
                throw new RuntimeException("Section not found in the YAML file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}