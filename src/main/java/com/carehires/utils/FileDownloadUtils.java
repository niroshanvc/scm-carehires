package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class FileDownloadUtils {

    private static final int DOWNLOAD_TIMEOUT_MS = 60000;
    private static final int POLLING_INTERVAL_MS = 1000;
    private static final String DOWNLOAD_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "resources" + File.separator + "downloads";

    private static final Logger logger = LogManager.getFormatterLogger(FileDownloadUtils.class);

    public static boolean deleteLatestDownloadedFile() {
        String latestFileName = getLatestDownloadedFileName();
        if (latestFileName != null) {
            File file = new File(DOWNLOAD_DIR, latestFileName);
            return file.delete();
        }
        return false;
    }

    public static String getLatestDownloadedFileName() {
        File downloadDir = new File(DOWNLOAD_DIR);
        logger.info("Download directory path: %s", DOWNLOAD_DIR);
        if (!downloadDir.exists() || !downloadDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid download directory: " + DOWNLOAD_DIR);
        }

        long startTime = System.currentTimeMillis();
        File downloadedFile;

        logger.info("Looking for PDF files in: %s", DOWNLOAD_DIR);
        while (System.currentTimeMillis() - startTime < DOWNLOAD_TIMEOUT_MS) {
            File[] files = downloadDir.listFiles((dir, name) -> name.endsWith(".pdf") && !name.contains(".crdownload"));
            if (files != null && files.length > 0) {
                logger.info("Found %s PDF file(s).", files.length);
                Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
                downloadedFile = files[0];

                long initialSize = downloadedFile.length();
                BasePage.genericWait(POLLING_INTERVAL_MS);
                long newSize = downloadedFile.length();

                if (initialSize == newSize && newSize > 0) { // Ensures file is stable and non-empty
                    logger.info("Download complete. File name: %s" , downloadedFile.getName());
                    return downloadedFile.getName();
                }
            } else {
                logger.info("No PDF file found yet, retrying...");
            }
            BasePage.genericWait(POLLING_INTERVAL_MS);
        }

        logger.info("File download timed out or no file detected.");
        return null;
    }

    public static void triggerDownloadAndCloseTab(WebElement element) {
        // Store the current window handle
        BasePage.genericWait(2000);
        String originalWindow = null;
        try {
            originalWindow = BasePage.getDriver().getWindowHandle();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }

        // Trigger the download that opens a new tab
        BasePage.clickWithJavaScript(element);

        // Wait for the new tab to open and switch to it
        String newTabHandle = null;
        try {
            for (String windowHandle : BasePage.getDriver().getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    BasePage.genericWait(2000);
                    BasePage.getDriver().switchTo().window(windowHandle);
                    newTabHandle = windowHandle;
                    break;
                }
            }
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }

        // Small wait to ensure the download process begins
        BasePage.genericWait(2000);

        // Check if download started by verifying files in the download directory
        File downloadDir = new File(FileDownloadUtils.DOWNLOAD_DIR);
        boolean downloadStarted = false;
        for (int i = 0; i < 5; i++) {  // Retry check
            if (Objects.requireNonNull(downloadDir.listFiles((dir, name) -> name.endsWith(".pdf"))).length > 0) {
                downloadStarted = true;
                break;
            }
            BasePage.genericWait(1000);
        }

        // Close the new tab if download started and avoid exceptions if already closed
        if (downloadStarted && newTabHandle != null) {
            try {
                BasePage.getDriver().switchTo().window(newTabHandle).close();
            } catch (NoSuchWindowException | BasePage.WebDriverInitializationException e) {
                logger.warn("New tab already closed.");
            }
        } else {
            logger.warn("Download may not have started as expected.");
        }

        // Switch back to the original tab
        try {
            BasePage.getDriver().switchTo().window(originalWindow);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }
}
