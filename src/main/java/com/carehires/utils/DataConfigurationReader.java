package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Random;

public class DataConfigurationReader {

    private static final Logger logger = LogManager.getLogger(DataConfigurationReader.class);

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
            } else {
                logger.error("Key not found in YAML: " + String.join(" -> ", keys));
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
}