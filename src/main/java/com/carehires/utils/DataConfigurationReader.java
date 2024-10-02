package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Random;

public class DataConfigurationReader {

    private static final Logger logger = LogManager.getLogger(DataConfigurationReader.class);
    private static int incrementValue = -1;  // Default value, indicating it hasn't been loaded yet
    private static final String INCREMENT_FILE_PATH = "src/main/resources/create_agency_increment_value.txt"; // Path to store increment value

    public static String readDataFromYmlFile(String fileName, String... keys) {
        String data = null;
        try (FileInputStream fis = new FileInputStream("src/main/resources/" + fileName + ".yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(fis);

            // Traverse the map using the keys
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

                // Check if the data contains a placeholder for unique number
                if (data.equals("<uniqueNumber>")) {
                    data = generateUniqueNumber();  // Replace with unique number
                }

                // Replace the {{increment}} placeholder with the current increment value
                if (data.contains("{{increment}}")) {
                    data = data.replace("{{increment}}", String.valueOf(getCurrentIncrementValue()));
                }

            } else {
                logger.error("Key not found in YAML: {}", String.join(" -> ", keys));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    // Method to generate a unique number with max 10 digits
    private static String generateUniqueNumber() {
        long seed = System.nanoTime();
        Random random = new Random(seed);
        return String.format("%010d", random.nextInt(1000000000));
    }

    // Method to get the current increment value only once and reuse it across different pages
    private static int getCurrentIncrementValue() {
        if (incrementValue == -1) {
            incrementValue = loadIncrementValueFromFile(); // Load from file at the start
        }
        return incrementValue; // Replace with actual logic
    }

    // Method to store the new increment value after use
    public static void storeNewIncrementValue() {
        incrementValue++; // Increment the value after processing all pages.
        saveIncrementValueToFile(incrementValue); // Save it to a file for persistence
    }

    // Load increment value from the file
    private static int loadIncrementValueFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INCREMENT_FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            logger.error("Failed to load increment value from file, defaulting to 170", e);
        }
        return 170; // Default value if the file doesn't exist or couldn't be read
    }

    // Save increment value to the file
    private static void saveIncrementValueToFile(int value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INCREMENT_FILE_PATH))) {
            writer.write(String.valueOf(value));
        } catch (IOException e) {
            logger.error("Failed to save increment value to file", e);
        }
    }
}