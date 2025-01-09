package com.carehires.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class BasePage {
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getFormatterLogger(BasePage.class);
    private static final Properties prop = new Properties();
    private static FileInputStream fis;
    private static final String HEADLESS = "--headless";
    private static final String JAVASCRIPT_CLICK = "arguments[0].click();";

    //for parallel execution
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private static final String RESOURCES_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "resources";

    private static final String DOWNLOAD_DIR = RESOURCES_DIR + File.separator + "downloads";

    private static void initializeDriver() throws WebDriverInitializationException {
        if (threadLocalDriver.get() == null) {
            String browser = getProperty("BROWSER");
            boolean isCIRunning = isCIEnvironment();
            logger.info("Initializing WebDriver for browser: %s", browser);

            try {
                switch (browser.toLowerCase()) {
                    case "chrome":
                        threadLocalDriver.set(setupChromeDriver(isCIRunning));
                        break;
                    case "firefox":
                        threadLocalDriver.set(setupFirefoxDriver(isCIRunning));
                        break;
                    case "edge":
                        threadLocalDriver.set(setupEdgeDriver(isCIRunning));
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
                logger.info("WebDriver initialized successfully.");
            } catch (Exception e) {
                logger.error("Error initializing WebDriver: ", e);
                throw new WebDriverInitializationException("Error initializing WebDriver for browser: " + browser, e);
            }
        }
    }

    public static class WebDriverInitializationException extends Exception {
        public WebDriverInitializationException(String message) {
            super(message);
        }

        public WebDriverInitializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static boolean isCIEnvironment() {
        return System.getenv("CI") != null || System.getenv("JENKINS_URL") != null;
    }

    private static WebDriver setupChromeDriver(boolean isCIRunning) {
        logger.info("****************** Setting up ChromeDriver.");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", getChromePreferences());
        options.addArguments("start-maximized", "--safebrowsing-disable-download-protection");

        if (isCIRunning) {
            configureCIOptions(options);
        }

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private static WebDriver setupFirefoxDriver(boolean isCIRunning) {
        logger.info("****************** Setting up FirefoxDriver.");
        FirefoxOptions options = new FirefoxOptions();

        if (isCIRunning) {
            options.addArguments(HEADLESS);
        }

        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }

    private static WebDriver setupEdgeDriver(boolean isCIRunning) {
        logger.info("****************** Setting up EdgeDriver.");
        EdgeOptions options = new EdgeOptions();

        if (isCIRunning) {
            configureCIOptions(options);
        }

        WebDriverManager.edgedriver().setup();
        return new EdgeDriver(options);
    }

    private static Map<String, Object> getChromePreferences() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", DOWNLOAD_DIR);
        prefs.put("download.prompt_for_download", false); // Disable download prompt
        prefs.put("download.directory_upgrade", true);
        prefs.put("plugins.always_open_pdf_externally", true); // Force PDFs to download
        prefs.put("safebrowsing.enabled", true);
        return prefs;
    }

    private static void configureCIOptions(MutableCapabilities options) {
        logger.info("****************** Running in CI environment, configuring headless options.");

        if (options instanceof ChromeOptions chromeOptions) {
            chromeOptions.addArguments(HEADLESS, "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        } else if (options instanceof FirefoxOptions firefoxOptions) {
            firefoxOptions.addArguments(HEADLESS);
        } else if (options instanceof EdgeOptions edgeOptions) {
            edgeOptions.addArguments(HEADLESS, "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        } else {
            logger.warn("Unsupported browser options class: %s", options.getClass().getSimpleName());
        }
    }

    // Static initializer block to load properties
    static {
        try (FileInputStream fis = new FileInputStream(RESOURCES_DIR + File.separator + "properties" + File.separator + "project.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static WebDriver getDriver() throws WebDriverInitializationException {
        if (threadLocalDriver.get() == null) {
            initializeDriver();
        }
        return threadLocalDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }

    public static void setUpDriver() throws WebDriverInitializationException {
        initializeDriver();
    }

    public static void tearDown() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.get().quit();
            threadLocalDriver.remove();
        }
    }

    public static void navigate(String urlKey) {
        String url = getProperty(urlKey);
        logger.info("****************** Navigating to %s", url);
        try {
            getDriver().get(url);
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitUntilElementPresent(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element present: %s, in seconds: %s", element, timeOutSeconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            logger.error("Element %s not visible after waiting for %s seconds.", element, timeOutSeconds);
        }
    }

    public static void clickAfterWait(WebElement element) {
        waitUntilElementPresent(element, 60);
        logger.info("****************** Clicked on the web element %s", element);
        element.click();
    }

    public static void scrollToWebElement(WebElement ele) {
        logger.info("****************** Scroll to web element %s", ele);
        JavascriptExecutor js = null;
        try {
            js = ((JavascriptExecutor) getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    public static void clickWithJavaScript(WebElement ele) {
        logger.info("****************** Clicking on the web element captured using webelement: %s", ele);
        waitUntilElementPresent(ele, 30);
        JavascriptExecutor js = null;
        try {
            js = (JavascriptExecutor) getDriver();
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        js.executeScript(JAVASCRIPT_CLICK, ele);
    }

    public static List<WebElement> findListOfWebElements(String xpath) {
        List<WebElement> els = null;
        try {
            By locator = By.xpath(xpath);
            els = getDriver().findElements(locator);
            waitUntilElementPresent(els.get(0), 90);
        } catch (Exception ex) {
            logger.error("****************** Object not found: %s", xpath);
        }
        return els;
    }

    public static void typeWithStringBuilder(WebElement element, String data) {
        logger.info("****************** Type using StringBuilder in %s", element);
        StringBuilder sb = new StringBuilder(data);
        waitUntilElementPresent(element, 30);
        element.clear();
        element.sendKeys(sb);
    }

    public static String getText(WebElement element) {
        logger.info("****************** Get text in %s", element);
        waitUntilElementPresent(element, 30);
        return element.getText();
    }

    public static String getAttributeValue(WebElement element, String attribute) {
        logger.info("******************  attribute value from %s and %s", element, attribute);
        waitUntilElementPresent(element, 30);
        return element.getAttribute(attribute);
    }

    public static void clickTabKey(WebElement element) {
        logger.info("****************** Click tab key in %s", element);
        element.sendKeys(Keys.TAB);
    }

    public static void waitUntilElementClickable(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element clickable: %s", element);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (WebDriverInitializationException e) {
            logger.error("Element was not clickable after waiting for %s seconds. Locator: %s", timeOutSeconds, element);
        }
    }

    public static void selectFirstOption(String xpath) {
        Select sel = null;
        try {
            sel = new Select(getDriver().findElement(By.xpath(xpath)));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        sel.selectByValue("0");
    }

    public static void clickShiftAndTabKeyTogether(WebElement element) {
        logger.info("****************** Click shift + tab key in %s", element);
        Actions actions = null;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        actions.keyDown(Keys.SHIFT)
                .sendKeys(Keys.TAB)
                .keyUp(Keys.SHIFT)
                .build()
                .perform();
    }

    public static void refreshPage() {
        logger.info("****************** Refreshing page");
        try {
            getDriver().navigate().refresh();
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearTexts(WebElement element) {
        logger.info("****************** Clear texts in %s", element);
        element.clear();
    }

    public static void moveToBottomOfThePage() {
        logger.info("****************** Move to bottom of the page.");
        JavascriptExecutor js = null;
        try {
            js = (JavascriptExecutor) getDriver();
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void waitFor(int milliseconds) {
        logger.info("****************** Wait for %s milliseconds", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToBottomOfPage() {
        logger.info("****************** Scroll down to bottom of the page.");
        Actions actions = null;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        actions.sendKeys(Keys.END).perform();
    }

    public static void clearAndEnterTexts(WebElement element, String texts) {
        logger.info("****************** Clear and enter texts in %s", element);
        waitUntilElementPresent(element, 30);
        element.clear();
        typeWithStringBuilder(element, texts);
    }

    public static String getPageTitle() {
        logger.info("****************** Getting actual page title");
        try {
            return getDriver().getTitle();
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits for the page to be fully loaded by checking the document's readyState.
     */
    public static void waitUntilPageCompletelyLoaded() {
        logger.info("****************** Wait until page completely loaded");
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(90));
            // Wait until the document's readyState is "complete"
            ExpectedCondition<Boolean> pageLoadCondition = driver -> {
                assert driver != null;
                try {
                    return Objects.equals(((JavascriptExecutor) getDriver()).executeScript("return document.readyState"), "complete");
                } catch (WebDriverInitializationException e) {
                    throw new RuntimeException(e);
                }
            };
            wait.until(pageLoadCondition);
        } catch (TimeoutException timeoutException) {
            logger.error("Page is not completely loaded after 90 seconds");
            throw timeoutException;
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mouseHoverAndClick(WebElement element, WebElement subElement, By childElement) {
        waitUntilElementPresent(element, 60);
        try {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).perform();
            waitUntilVisibilityOfElementLocated(childElement, 60);

            if (subElement.isDisplayed() && subElement.isEnabled()) {
                actions.moveToElement(subElement).click().perform();
            } else {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                js.executeScript(JAVASCRIPT_CLICK, subElement);
            }
        } catch (Exception e) {
            logger.error("Failed to perform mouse hover and click: message: %s and error: %s", e.getMessage(), e);
        }
    }

    //upload file
    public static void uploadFile(WebElement element, String filePath) {
        logger.info("****************** Uploading file: %s to %s", filePath, element);
        element.sendKeys(filePath);
    }

    public static void genericWait(int milliseconds) {
        try {
            logger.info("****************** Waiting for %s milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.info(String.valueOf(Level.WARN), "Interrupted", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public static void uploadFile(String filePath) throws AWTException {
        // Set the file path to the clipboard
        logger.info("****************** Uploading file using ROBOT framework: %s", filePath);
        setClipboardData(filePath);

        // Create an instance of Robot class
        Robot robot = new Robot();

        // Press Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Press Ctrl+V (paste)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter again to confirm the file selection
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void clickWithJavaScript(String xpath) {
        logger.info("****************** Clicking on the web element captured using xpath: %s", xpath);
        WebElement ele = null;
        try {
            ele = getDriver().findElement(By.xpath(xpath));
            waitUntilElementPresent(ele, 30);
            JavascriptExecutor js;
            js = (JavascriptExecutor) getDriver();
            js.executeScript(JAVASCRIPT_CLICK, ele);

        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentUrl() {
        try {
            return getDriver().getCurrentUrl();
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clickOnEnterKey(WebElement element) {
        logger.info("****************** Click on enter key");
        element.sendKeys(Keys.ENTER);
    }

    public static void typeWithStringBuilderAndDelay(WebElement element, String data, int milliseconds) {
        logger.info("****************** Type using StringBuilder in %s with %d milliseconds delay", element, milliseconds);
        StringBuilder sb = new StringBuilder(data);
        waitUntilElementPresent(element, 30);
        String[] strs = sb.toString().split("");
        for (String  character : strs) {
            element.sendKeys(character);
            genericWait(milliseconds);
        }
    }

    public static void waitUntilElementClickable(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element clickable at xpath: %s", xpath);
        WebElement element = null;
        try {
            element = getDriver().findElement(By.xpath(xpath));
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean isElementDisplayed(WebElement element) {
        logger.info("****************** Checking element - %s is displayed:", element);
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void waitUntilElementDisplayed(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed: %s, and seconds: %s", element, seconds);

        // Create a wait instance with the provided timeout
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            logger.error("Element was not displayed after waiting for %s seconds. Locator: %s", seconds, element);
        }
    }

    public static List<WebElement> findListOfWebElements(By locator) {
        List<WebElement> els = null;
        try {
            els = getDriver().findElements(locator);
        } catch (Exception ex) {
            logger.error("****************** Object not found at the locator: %s", locator);
        }
        return els;
    }

    public static void mouseHoverOverElement(WebElement element) {
        waitUntilElementClickable(element, 30);
        Actions actions = null;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        actions.moveToElement(element).build().perform();
    }

    public static void waitUntilElementDisappeared(WebElement element, int seconds) {
        logger.info("****************** Wait until element disappeared: %s, and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitAndIgnoreStaleException(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed by ignoring stale element exception: %s, " +
                "and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static boolean isElementEnabled(WebElement element) {
        logger.info("****************** Checking element - %s is enabled:", element);
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static WebDriverWait webdriverWait(int seconds) {
        logger.info("****************** Waiting for %s seconds", seconds);
        try {
            return new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitUntilElementPresent(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element present at xpath: %s, in seconds: %s", xpath, timeOutSeconds);
        WebElement element = null;
        try {
            element = getDriver().findElement(By.xpath(xpath));
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void scrollToWebElement(String xpath) {
        logger.info("****************** Scroll to web element at xpath %s", xpath);
        JavascriptExecutor js = null;
        try {
            js = ((JavascriptExecutor) getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        WebElement element = null;
        try {
            element = getDriver().findElement(By.xpath(xpath));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitUntilVisibilityOfElementLocated(By locator, int seconds) {
        logger.info("****************** Wait until visibility of element located by %s, and seconds: %s", locator, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element was not visible after waiting for %s seconds. Locator: %s", seconds, locator);
        }
    }

    public static void waitUntilElementAttributeGetChanged(WebElement element, String attribute, String attributeUpdatedValue, int seconds) {
        logger.info("****************** Wait until element attribute get changed: %s, and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        try {
            wait.until((ExpectedCondition<Boolean>) driver -> {
                assert driver != null;
                String attributeValue = null;
                try {
                    attributeValue = Objects.requireNonNull(((JavascriptExecutor) getDriver()).executeScript
                            ("return arguments[0].getAttribute(arguments[1]);", element, attribute)).toString();
                } catch (WebDriverInitializationException e) {
                    throw new RuntimeException(e);
                }
                assert attributeValue != null;
                return attributeValue.equals(attributeUpdatedValue);
            });
        } catch (TimeoutException e) {
            logger.error("Element attribute was not changed after waiting for %s seconds. Locator: %s", seconds, element);
        }
    }

    /**
     * Verifies if an element is present on the page after waiting for a specified time using Hamcrest assertion.
     *
     * @param locator     The By locator for the element.
     * @param timeOutSeconds The number of seconds to wait for the element to be present.
     */
    public static void verifyElementIsPresentAfterWait(By locator, int timeOutSeconds) {
        logger.info("****************** Verify element present: %s, in seconds: %s", locator, timeOutSeconds);

        // Create a WebDriverWait instance
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }

        // Wait until at least one element is present
        wait.until((ExpectedCondition<Boolean>) driver -> {
            assert driver != null;
            List<WebElement> elements = null;
            try {
                elements = getDriver().findElements(locator);
            } catch (WebDriverInitializationException e) {
                throw new RuntimeException(e);
            }
            return !elements.isEmpty(); // Return true if at least one element is found
        });

        // After waiting, assert that the element is present
        List<WebElement> elements = null;
        try {
            elements = getDriver().findElements(locator);
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        MatcherAssert.assertThat("Element should be present", elements.size(), Matchers.greaterThan(0));
    }

    // mouse hover and release webelement
    public static void mouseHoverAndRelease(WebElement element, WebElement subElement) {
        waitUntilElementClickable(element, 30);
        Actions actions = null;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        actions.moveToElement(element);
        actions.moveToElement(subElement);
        actions.release().build().perform();
    }

    public static void waitUntilElementRefreshedAndClickable(WebElement element, int timeOutSeconds) {
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
        } catch (TimeoutException e) {
            logger.error("Element not clickable even after refreshing: %s", element);
            throw e;
        }
    }

    public static void waitUntilElementClickable(By locator, int timeoutInSeconds) {
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void mouseHoverAndClick(By mainLocator, By subLocator) {
        WebElement mainLink = wait.until(ExpectedConditions.visibilityOfElementLocated(mainLocator));
        waitUntilElementPresent(mainLink, 60);
        Actions actions = null;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        actions.moveToElement(mainLink).perform();
        WebElement subMenu = wait.until(ExpectedConditions.presenceOfElementLocated(subLocator));
        genericWait(300);
        actions.moveToElement(subMenu);
        actions.click(subMenu).build().perform();
        BasePage.waitUntilPageCompletelyLoaded();
    }

    public static void waitUntilPresenceOfElementLocated(By locator, int timeoutSeconds) {
        logger.info("****************** Wait until element present: %s", locator);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (WebDriverInitializationException e) {
            logger.error("Element was not present after waiting for %s seconds. Locator: %s", timeoutSeconds, locator);
        }
    }

    public static void waitUntilDisabledAttributeDisappears(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element attribute disappeared: %s", element);

        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        wait.until(driver -> {
            try {
                try {
                    return Objects.requireNonNull(((JavascriptExecutor) getDriver())
                            .executeScript("return arguments[0].getAttribute('disabled') == null;", element));
                } catch (WebDriverInitializationException e) {
                    throw new RuntimeException(e);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
