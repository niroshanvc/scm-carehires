package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("ALL")
public class FileWriterUtils {

    private static final Logger logger = LogManager.getFormatterLogger(FileWriterUtils.class);

    private FileWriterUtils() {
    }

    public static void writeJobIdToAFile(String text, String fileName) {
        logger.info("Writing on text file: %s and texts: %s ", fileName, text);
        File file = new File(fileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            logger.error("Unable to write on file %s ", e.toString());
        }
    }

    public static void clearContent(String fileName) {
        logger.info("Clearing content of file: %s ", fileName);
        File file = new File(fileName);

        try(FileWriter fileWriter = new FileWriter(file, false)) {
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
        } catch (IOException ioe) {
            logger.error("Unable to clear the contents on %s ", ioe.toString());
        }
    }

    public static FileWriterUtils createFileWriterUtils() {
        return new FileWriterUtils();
    }
}
