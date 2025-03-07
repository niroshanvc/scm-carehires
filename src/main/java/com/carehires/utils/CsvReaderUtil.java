package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CsvReaderUtil {

    private CsvReaderUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger logger = LogManager.getFormatterLogger(CsvReaderUtil.class);

    public static List<String> readCsvFile(String fileName) {
        logger.info("Reading values in csv file called: %s", fileName );
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // there is only one row to be read
            if (line != null) {
                return Arrays.asList(line.split(","));
            }
        } catch (IOException ex) {
            logger.error("Unable to read the csv file.");
        }
        return Collections.emptyList(); // Return null if the file is not found or an error occurs
    }
}
