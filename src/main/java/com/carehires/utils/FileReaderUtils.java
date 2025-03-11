package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileReaderUtils {

    private static final Logger logger = LogManager.getFormatterLogger(FileReaderUtils.class);

    public static int readIncrementValue(String filePath) {
        int value = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            value = Integer.parseInt(reader.readLine().trim());
        } catch (Exception e) {
            logger.error("Unable to read the file!");
        }
        return value;
    }
}
