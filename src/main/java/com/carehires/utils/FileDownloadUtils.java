package com.carehires.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

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
        WebDriver driver;
        String originalWindow = null;
        // Initialize logger, assuming you have one (e.g., SLF4J)
        // private static final Logger logger = LoggerFactory.getLogger(YourClassName.class);


        try {
            driver = BasePage.getDriver(); // Get your WebDriver instance
            originalWindow = driver.getWindowHandle();
        } catch (BasePage.WebDriverInitializationException e) {
            // logger.error("Failed to get WebDriver instance or original window handle.", e);
            throw new RuntimeException("Failed to get WebDriver instance or original window handle.", e);
        }

        Set<String> handlesBeforeClick = driver.getWindowHandles();
        BasePage.clickWithJavaScript(element); // Trigger the download that might open a new tab

        String newTabHandle = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as necessary

        try {
            final int currentWindowCount = handlesBeforeClick.size();
            wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    return d.getWindowHandles().size() > currentWindowCount;
                }
                @Override
                public String toString() {
                    return String.format("number of windows to be greater than %d", currentWindowCount);
                }
            });

            Set<String> handlesAfterClick = driver.getWindowHandles();
            for (String handle : handlesAfterClick) {
                if (!handlesBeforeClick.contains(handle)) {
                    newTabHandle = handle;
                    break;
                }
            }

            if (newTabHandle != null) {
                // The error was occurring here. The explicit wait above should make this more reliable.
                driver.switchTo().window(newTabHandle);
                logger.info("Successfully switched to new tab: %s", newTabHandle);
                // Optional: Short wait if the new tab needs a moment to initiate the download fully.
                BasePage.genericWait(500); // Reduced wait, or make it conditional
            } else {
                logger.warn("No new tab was identified after click. Download might be happening in the original " +
                        "tab or a quickly closed tab.");
            }

        } catch (TimeoutException e) {
            logger.warn("Timed out waiting for a new window/tab to open. Proceeding with download check " +
                    "in original tab.", e);
            // newTabHandle will remain null, indicating no new tab to close.
        } catch (NoSuchWindowException e) {
            // This can happen if, despite the wait, the window closes just as we try to switch.
            logger.warn("NoSuchWindowException while trying to switch to the new tab. It might have " +
                    "closed prematurely. Handle: %s", newTabHandle, e);
            newTabHandle = null; // Mark as null so we don't try to close it.
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("WebDriver initialization error during window handling.", e);
            throw new RuntimeException("WebDriver initialization error during window handling.", e);
        }

        // Small wait to ensure the download process begins (copied from your original logic)
        // This might need to be adjusted or made more robust (e.g., waiting for file to exist)
        BasePage.genericWait(2000);

        // Check if download started (copied from your original logic)
        File downloadDir = new File(FileDownloadUtils.DOWNLOAD_DIR);
        boolean downloadStarted = false;
        for (int i = 0; i < 5; i++) { // Retry check
            if (Objects.requireNonNull(downloadDir.listFiles((dir, name) -> name.endsWith(".pdf")))
                    .length > 0) {
                downloadStarted = true;
                break;
            }
            BasePage.genericWait(1000);
        }

        // Close the new tab if it was identified and we successfully switched to it
        if (newTabHandle != null) {
            try {
                // Ensure we are on the new tab (or switch back if focus somehow shifted)
                // before attempting to close. driver.close() closes the current focused window.
                if (!driver.getWindowHandle().equals(newTabHandle) && driver.getWindowHandles().contains(newTabHandle)) {
                    driver.switchTo().window(newTabHandle);
                }
                // Only close if the current window is the new tab
                if (driver.getWindowHandle().equals(newTabHandle)) {
                    driver.close();
                    logger.info("Closed new tab: %s", newTabHandle);
                }
            } catch (NoSuchWindowException e) {
                logger.warn("New tab %s already closed before explicit close operation.", newTabHandle, e);
            } catch (Exception e) { // Catch broader exceptions during close
                 logger.error("Error closing new tab %s: ", newTabHandle, e);
            }
        } else if (!downloadStarted) {
             logger.warn("Download may not have started as expected, and no new tab was handled.");
        }


        // Switch back to the original tab
        try {
            // Ensure the original window still exists before trying to switch.
            if (driver.getWindowHandles().contains(originalWindow)) {
                driver.switchTo().window(originalWindow);
                 logger.info("Successfully switched back to original window: %s", originalWindow);
            } else {
                 logger.warn("Original window %s no longer exists. Attempting to switch to any available " +
                         "window.", originalWindow);
                // Fallback: if original window is gone, try to switch to any other available window.
                Set<String> remainingHandles = driver.getWindowHandles();
                if (!remainingHandles.isEmpty()) {
                    driver.switchTo().window(remainingHandles.iterator().next());
                     logger.info("Switched to a remaining window: %s", driver.getWindowHandle());
                } else {
                     logger.error("No windows available to switch back to. Test state might be compromised.");
                    // This could be an issue if the main application window also closed.
                }
            }
        } catch (NoSuchWindowException e) {
             logger.error("Failed to switch back to original window %s as it no longer exists.",
                     originalWindow, e);
            // This is a critical state, the test might not be able to continue as expected.
        }
    }
}
