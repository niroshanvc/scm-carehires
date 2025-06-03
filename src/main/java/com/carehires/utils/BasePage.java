package com.carehires.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import java.util.function.Supplier;

public class BasePage {
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getFormatterLogger(BasePage.class);
    private static final Properties prop = new Properties();
    private static FileInputStream fis;
    // private static final String HEADLESS = "--headless"; // Using "--headless=new" is preferred
    private static final String JAVASCRIPT_CLICK = "arguments[0].click();";

    //for parallel execution
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private static final String RESOURCES_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "resources";

    private static final String DOWNLOAD_DIR = RESOURCES_DIR + File.separator + "downloads";

    private static void initializeDriver() throws WebDriverInitializationException {
        if (threadLocalDriver.get() == null) {
            String browser = getProperty("BROWSER");
            // boolean isCIRunning = isCIEnvironment(); // We'll use the system property for headless
            logger.info("Initializing WebDriver for browser: %s", browser);

            try {
                switch (browser.toLowerCase()) {
                    case "chrome":
                        threadLocalDriver.set(setupChromeDriver());
                        break;
                    case "firefox":
                        threadLocalDriver.set(setupFirefoxDriver());
                        break;
                    case "edge":
                        threadLocalDriver.set(setupEdgeDriver());
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
                // Set common timeouts after driver initialization
                if (threadLocalDriver.get() != null) {
                    threadLocalDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Example
                    threadLocalDriver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(getProperty("PAGE_LOAD_TIMEOUT", "60"))));
                    threadLocalDriver.get().manage().timeouts().scriptTimeout(Duration.ofSeconds(Long.parseLong(getProperty("SCRIPT_TIMEOUT", "30"))));
                }
                logger.info("WebDriver initialized successfully.");
            } catch (Exception e) {
                logger.error("Error initializing WebDriver: ", e);
                throw new WebDriverInitializationException("Error initializing WebDriver for browser: " + browser, e);
            }
        }
    }

    public static void waitUntil(Supplier<Boolean> condition, int timeoutInSeconds) {
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.jsReturnsValue("return (" + condition.get() + ");")));
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
        // This can still be useful for other CI-specific logic if needed
        return System.getenv("CI") != null || System.getenv("CIRCLECI") != null || System.getenv("JENKINS_URL") != null;
    }

    private static WebDriver setupChromeDriver() {
        logger.info("****************** Setting up ChromeDriver.");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", getChromePreferences());
        options.addArguments("start-maximized"); // Start maximized for headed mode
        options.addArguments("--safebrowsing-disable-download-protection"); // Keep this if needed

        // Read headless mode from system property (set by CircleCI Maven command)
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("webdriver.chrome.headless", "false"));

        if (isHeadless) {
            logger.info("****************** Configuring ChromeDriver for HEADLESS execution.");
            options.addArguments("--headless=new"); // Use the new headless mode
            options.addArguments("--window-size=1920,1080"); // Crucial for headless stability
            options.addArguments("--no-sandbox"); // Often required in Linux/Docker CI environments
            options.addArguments("--disable-dev-shm-usage"); // Overcomes resource limitations in Docker
            options.addArguments("--disable-gpu"); // Generally recommended for headless
            // options.addArguments("--remote-debugging-port=9222"); // Optional: for debugging CI if possible
        } else {
            logger.info("****************** Configuring ChromeDriver for HEADED execution.");
        }

        // Add other general arguments if needed, e.g.
        // options.addArguments("--disable-extensions");
        // options.addArguments("--ignore-certificate-errors");


        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private static WebDriver setupFirefoxDriver() {
        logger.info("****************** Setting up FirefoxDriver.");
        FirefoxOptions options = new FirefoxOptions();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("webdriver.firefox.headless", "false"));


        if (isHeadless || isCIEnvironment()) { // Fallback to CI check if specific property not set
            logger.info("****************** Configuring FirefoxDriver for HEADLESS execution.");
            options.addArguments("--headless");
        } else {
            logger.info("****************** Configuring FirefoxDriver for HEADED execution.");
        }


        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }

    private static WebDriver setupEdgeDriver() {
        logger.info("****************** Setting up EdgeDriver.");
        EdgeOptions options = new EdgeOptions();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("webdriver.edge.headless", "false"));

        if (isHeadless || isCIEnvironment()) { // Fallback to CI check
            logger.info("****************** Configuring EdgeDriver for HEADLESS execution.");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu"); // Similar to Chrome
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            logger.info("****************** Configuring EdgeDriver for HEADED execution.");
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

    // This method might be redundant if headless options are set directly in setupXXXDriver methods
    // Or it can be used for other non-headless CI specific configurations.
    // For now, the headless specific parts are integrated into setupChromeDriver.
    /*
    private static void configureCIOptions(MutableCapabilities options) {
        logger.info("****************** Running in CI environment, configuring CI-specific options.");
        // This method can be used for other CI specific settings if needed,
        // but headless for Chrome is now handled by the system property.
        if (options instanceof ChromeOptions chromeOptions) {
            // Arguments like --no-sandbox and --disable-dev-shm-usage are good for CI
            // even if not strictly headless, but usually applied with headless.
            // chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        }
        // ... similar for other browsers if there are non-headless CI specific settings
    }
    */

    // Static initializer block to load properties
    static {
        try {
            // Corrected path for fis initialization
            fis = new FileInputStream(RESOURCES_DIR + File.separator + "properties" + File.separator + "project.properties");
            prop.load(fis);
        } catch (IOException e) {
            logger.error("An error occurred while loading project.properties: ", e);
            // Consider re-throwing as a runtime exception if properties are critical
            throw new WebDriverRuntimeException(new WebDriverInitializationException("Failed to load project.properties", e));
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("An error occurred while closing FileInputStream: ", e);
                }
            }
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
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
            try {
                threadLocalDriver.get().quit();
            } catch (Exception e) {
                logger.error("Error during WebDriver quit: " + e.getMessage());
            } finally {
                threadLocalDriver.remove();
            }
        }
    }

    public static void navigate(String urlKey) {
        String url = getProperty(urlKey);
        logger.info("****************** Navigating to %s", url);
        try {
            getDriver().get(url);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementPresent(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element present: %s, in seconds: %s", element, timeOutSeconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            // This exception is for driver init, WebDriverWait will throw TimeoutException
            logger.error("WebDriver not initialized while waiting for element: %s", element, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element %s not visible after waiting for %s seconds.", element, timeOutSeconds);
            throw e; // Re-throw the TimeoutException
        }
    }

    public static void clickAfterWait(WebElement element) {
        // Defaulting to a reasonable timeout, e.g., 60 seconds.
        // Consider making this configurable or passing it as a parameter.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICK_TIMEOUT", "60"));
        waitUntilElementClickable(element, defaultTimeout); // Use waitUntilElementClickable
        logger.info("****************** Clicked on the web element %s", element);
        element.click();
    }

    public static void scrollToWebElement(WebElement ele) {
        logger.info("****************** Scroll to web element %s", ele);
        JavascriptExecutor js;
        try {
            js = ((JavascriptExecutor) getDriver());
            js.executeScript("arguments[0].scrollIntoView(true);", ele);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void clickWithJavaScript(WebElement ele) {
        logger.info("****************** Clicking on the web element captured using webelement: %s", ele);
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(ele, defaultTimeout);
        JavascriptExecutor js;
        try {
            js = (JavascriptExecutor) getDriver();
            js.executeScript(JAVASCRIPT_CLICK, ele);
            logger.info("JavaScript click executed successfully on %s", ele);
        } catch (WebDriverInitializationException e) {
            logger.error("JavaScript click failed on element: %s", e.toString());
            throw new WebDriverRuntimeException(e);
        }
    }

    public static List<WebElement> findListOfWebElements(String xpath) {
        List<WebElement> els = null;
        try {
            By locator = By.xpath(xpath);
            els = getDriver().findElements(locator);
            if (els != null && !els.isEmpty()) {
                // Defaulting to a reasonable timeout.
                int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "90"));
                waitUntilElementPresent(els.get(0), defaultTimeout);
            } else {
                logger.info("****************** No elements found for xpath: %s", xpath);
            }
        } catch (Exception ex) {
            logger.error("****************** Object not found or error during findListOfWebElements for xpath: %s. Error: %s", xpath, ex.getMessage());
        }
        return els;
    }

    public static void typeWithStringBuilder(WebElement element, String data) {
        logger.info("****************** Type using StringBuilder in %s", element);
        StringBuilder sb = new StringBuilder(data);
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        element.clear();
        element.sendKeys(sb);
    }

    public static void sendKeys(WebElement element, String data) {
        logger.info("****************** Send keys in %s", element);
        // It's good practice to ensure element is present/visible before sendKeys
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        element.sendKeys(data);
    }

    public static void sendKeys(By by, String data) {
        logger.info("****************** Type using by locator in %s", by);
        WebElement element = null;
        try {
            // Defaulting to a reasonable timeout.
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
            element = new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            element.clear();
            element.sendKeys(data);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for element %s to be visible before sending keys.", by);
            throw e;
        }
    }

    public static String getText(WebElement element) {
        logger.info("****************** Get text in %s", element);
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        return element.getText();
    }

    public static String getTextWithoutWait(WebElement element) {
        logger.info("****************** Without waiting any second, get text in %s", element);
        // Be cautious with this method, as it might lead to StaleElementReferenceException
        // if the element is not immediately available or changes.
        try {
            return element.getText();
        } catch (StaleElementReferenceException e) {
            logger.warn("StaleElementReferenceException while getting text without wait for %s. Attempting to re-find.", element);
            // Attempt to re-find the element - this requires knowing its locator, which is not available here.
            // This method is inherently risky. Consider passing locator or ensuring element is stable.
            throw e;
        }
    }

    public static String getAttributeValue(WebElement element, String attribute) {
        logger.info("****************** attribute value from element %s and %s", element, attribute);
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        return element.getAttribute(attribute);
    }

    public static String getAttributeValue(String xpath, String attribute) {
        logger.info("****************** attribute value from xpath %s and attribute %s", xpath, attribute);
        By by = By.xpath(xpath);
        WebElement ele;
        try {
            // Defaulting to a reasonable timeout.
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
            ele = new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            return ele.getAttribute(attribute);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for element %s to be visible before getting attribute.", xpath);
            throw e;
        }
    }

    public static void clickTabKey(WebElement element) {
        logger.info("****************** Click tab key in %s", element);
        // Ensure element is interactable first
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "30"));
        waitUntilElementClickable(element, defaultTimeout);
        element.sendKeys(Keys.TAB);
    }

    public static void waitUntilElementClickable(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element clickable: %s", element);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (WebDriverInitializationException e) {
            logger.error("WebDriver not initialized while waiting for element %s to be clickable.", element, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element was not clickable after waiting for %s seconds. Element: %s", timeOutSeconds, element.toString());
            // It's important to re-throw the original TimeoutException so test frameworks can handle it.
            throw e;
        }
    }

    public static void selectFirstOption(String xpath) {
        Select sel;
        try {
            // Defaulting to a reasonable timeout.
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
            WebElement dropdownElement = new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            sel = new Select(dropdownElement);
            if (!sel.getOptions().isEmpty()) {
                sel.selectByIndex(0); // selectByValue("0") might not always work if value "0" isn't the first.
            } else {
                logger.warn("Dropdown at xpath %s has no options to select.", xpath);
            }
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for dropdown %s to be visible.", xpath);
            throw e;
        }
    }

    public static void clickShiftAndTabKeyTogether(WebElement element) {
        logger.info("****************** Click shift + tab key in %s", element);
        Actions actions;
        try {
            // Ensure element is interactable
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "30"));
            waitUntilElementClickable(element, defaultTimeout);
            actions = new Actions(getDriver());
            actions.keyDown(Keys.SHIFT)
                    .sendKeys(Keys.TAB)
                    .keyUp(Keys.SHIFT)
                    .build()
                    .perform();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    // double-click on an element
    public static void doubleClick(WebElement element) {
        Actions actions;
        try {
            // Ensure element is interactable
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "30"));
            waitUntilElementClickable(element, defaultTimeout);
            actions = new Actions(getDriver());
            actions.doubleClick(element).perform();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void refreshPage() {
        logger.info("****************** Refreshing page");
        try {
            getDriver().navigate().refresh();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void clearTexts(WebElement element) {
        logger.info("****************** Clear texts in %s", element);
        // Ensure element is present
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        element.clear();
    }

    public static void clearTextsUsingSendKeys(WebElement element) {
        logger.info("****************** Clear texts in %s using Send Keys", element);
        // Ensure element is present
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }


    public static void moveToBottomOfThePage() {
        logger.info("****************** Move to bottom of the page.");
        JavascriptExecutor js;
        try {
            js = (JavascriptExecutor) getDriver();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void scrollToBottomOfPage() {
        logger.info("****************** Scroll down to bottom of the page.");
        Actions actions;
        try {
            actions = new Actions(getDriver());
            actions.sendKeys(Keys.END).perform();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void clearAndEnterTexts(WebElement element, String texts) {
        logger.info("****************** Clear and enter texts in %s", element);
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        element.clear();
        // Using sendKeys directly after clear is often more reliable than typeWithStringBuilder for this combined action
        element.sendKeys(texts);
        // typeWithStringBuilder(element, texts); // Or keep this if it works better for your specific inputs
    }

    public static String getPageTitle() {
        logger.info("****************** Getting actual page title");
        try {
            return getDriver().getTitle();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    /**
     * Waits for the page to be fully loaded by checking the document's readyState.
     */
    public static void waitUntilPageCompletelyLoaded() {
        logger.info("****************** Wait until page completely loaded");
        try {
            // Defaulting to a reasonable timeout.
            int pageLoadTimeout = Integer.parseInt(getProperty("PAGE_COMPLETE_LOAD_TIMEOUT", "90"));
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(pageLoadTimeout));
            ExpectedCondition<Boolean> pageLoadCondition = driver -> {
                assert driver != null;
                try {
                    return Objects.equals(((JavascriptExecutor) getDriver()).executeScript("return document.readyState"), "complete");
                } catch (WebDriverInitializationException e) {
                    // This specific catch might be problematic if getDriver() itself throws during an unstable phase.
                    // Consider if this should be a WebDriverRuntimeException or if the outer catch handles it.
                    logger.error("WebDriverInitializationException during page load check", e);
                    return false; // Or rethrow
                }
            };
            wait.until(pageLoadCondition);
        } catch (TimeoutException timeoutException) {
            logger.error("Page is not completely loaded after configured timeout.");
            throw timeoutException;
        } catch (WebDriverInitializationException e) {
            // This would catch issues with getDriver() in the WebDriverWait constructor
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void mouseHoverAndClick(WebElement element, WebElement subElement, By childElement) {
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "60"));
        waitUntilElementClickable(element, defaultTimeout); // Ensure main element is at least clickable (implies visibility)
        try {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).perform();

            // Wait for the child/sub-element to be visible and then clickable
            WebElement clickableSubElement = new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout))
                    .until(ExpectedConditions.elementToBeClickable(subElement)); // Prefer waiting on subElement directly

            // Check visibility and enabled again just before click, though elementToBeClickable should cover this.
            if (clickableSubElement.isDisplayed() && clickableSubElement.isEnabled()) {
                actions.moveToElement(clickableSubElement).click().perform();
            } else {
                logger.warn("Sub-element %s was not interactable, attempting JS click.", subElement);
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                js.executeScript(JAVASCRIPT_CLICK, clickableSubElement);
            }
        } catch (Exception e) {
            logger.error("Failed to perform mouse hover and click: message: %s and error: %s", e.getMessage(), e);
            // Consider re-throwing a custom exception or the original one
            throw new RuntimeException("Mouse hover and click failed", e);
        }
    }

    //upload file
    public static void uploadFile(WebElement element, String filePath) {
        logger.info("****************** Uploading file: %s to %s", filePath, element);
        // For <input type="file">, it should be directly interactable without explicit waits usually,
        // but ensuring it's present can be good. It doesn't need to be "visible" in the traditional sense.
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']"))); // Example generic wait for such an element
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        element.sendKeys(filePath);
    }

    public static void genericWait(int milliseconds) {
        try {
            logger.info("****************** Waiting for %s milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Generic wait interrupted", e); // Corrected logging level and message
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
        robot.setAutoDelay(500); // Add a small delay between robot actions

        // Press Enter (to open file dialog if needed, or confirm if already open) - This behavior is OS/App dependent
        // robot.keyPress(KeyEvent.VK_ENTER);
        // robot.keyRelease(KeyEvent.VK_ENTER);
        // genericWait(500); // Wait for dialog

        // Press Ctrl+V (paste)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        genericWait(500); // Wait for paste to complete

        // Press Enter again to confirm the file selection
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void clickWithJavaScript(String xpath) {
        logger.info("****************** Clicking on the web element captured using xpath: %s", xpath);
        WebElement ele;
        try {
            // Defaulting to a reasonable timeout.
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
            // Wait for presence first, then attempt JS click
            ele = new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            JavascriptExecutor js;
            js = (JavascriptExecutor) getDriver();
            js.executeScript(JAVASCRIPT_CLICK, ele);

        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for element %s to be present before JS click.", xpath);
            throw e;
        }
    }

    public static String getCurrentUrl() {
        try {
            return getDriver().getCurrentUrl();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void clickOnEnterKey(WebElement element) {
        logger.info("****************** Click on enter key");
        // Ensure element is interactable
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "30"));
        waitUntilElementClickable(element, defaultTimeout);
        element.sendKeys(Keys.ENTER);
    }

    public static void typeWithStringBuilderAndDelay(WebElement element, String data, int milliseconds) {
        logger.info("****************** Type using StringBuilder in %s with %d milliseconds delay", element, milliseconds);
        StringBuilder sb = new StringBuilder(data);
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "30"));
        waitUntilElementPresent(element, defaultTimeout);
        element.clear(); // Clear before typing
        String[] strs = sb.toString().split(""); // Consider iterating over char array directly
        for (String character : strs) {
            element.sendKeys(character);
            genericWait(milliseconds);
        }
    }

    public static void waitUntilElementClickable(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element clickable at xpath: %s", xpath);
        WebElement element;
        try {
            // Find element first, then wait for clickability
            element = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            // Now wait for this specific element to be clickable
            new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element at xpath %s was not clickable after %s seconds", xpath, timeOutSeconds);
            throw e;
        }
    }

    public static boolean isElementDisplayed(WebElement element) {
        logger.info("****************** Checking element - %s is displayed:", element);
        try {
            // It's better to wait for a short period for visibility rather than immediate check
            return new WebDriverWait(getDriver(), Duration.ofSeconds(5)) // Short wait
                    .until(ExpectedConditions.visibilityOf(element)) != null;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static boolean isElementDisplayed(String xpath) {
        logger.info("******************** Checking element - %s is displayed:", xpath);
        try {
            WebElement element = new WebDriverWait(getDriver(), Duration.ofSeconds(5)) // Short wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return element != null;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementDisplayed(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed: %s, and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            logger.error("WebDriver not initialized while waiting for element %s to be displayed.", element, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element was not displayed after waiting for %s seconds. Element: %s", seconds, element.toString());
            throw e;
        }
    }

    public static List<WebElement> findListOfWebElements(By locator) {
        List<WebElement> els = null;
        try {
            // Wait for at least one element to be present to avoid immediate empty list if page is loading
            new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
            els = getDriver().findElements(locator);
        } catch (Exception ex) {
            logger.error("****************** Object not found at the locator: %s. Error: %s", locator, ex.getMessage());
        }
        return els != null ? els : List.of(); // Return empty list if null
    }

    public static List<WebElement> listOfWebElements(String xpath) {
        List<WebElement> els = null;
        try {
            By locator = By.xpath(xpath);
            new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
            els = getDriver().findElements(locator);
        } catch (Exception ex) {
            logger.error("******************* Object not found at the locator: %s. Error: %s", xpath, ex.getMessage());
        }
        return els != null ? els : List.of(); // Return empty list if null
    }

    public static void mouseHoverOverElement(WebElement element) {
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "30"));
        waitUntilElementClickable(element, defaultTimeout); // Ensure it's interactable
        Actions actions;
        try {
            actions = new Actions(getDriver());
            actions.moveToElement(element).build().perform();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementDisappeared(WebElement element, int seconds) {
        logger.info("****************** Wait until element disappeared: %s, and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (WebDriverInitializationException e) {
            logger.error("WebDriver not initialized while waiting for element %s to disappear.", element, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.warn("Element %s did not disappear after %s seconds.", element.toString(), seconds);
            // Depending on test logic, this might not be an error to throw.
        }
    }

    public static void waitAndIgnoreStaleException(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed by ignoring stale element exception: %s, " +
                "and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element %s not visible (even ignoring StaleElementReferenceException) after %s seconds.", element.toString(), seconds);
            throw e;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        logger.info("****************** Checking element - %s is enabled:", element);
        try {
            // Wait for element to be present before checking if enabled
            new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(element));
            return element.isEnabled();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static WebDriverWait webdriverWait(int seconds) {
        logger.info("****************** Waiting for %s seconds", seconds);
        try {
            return new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementPresent(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element present at xpath: %s, in seconds: %s", xpath, timeOutSeconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            // Wait for presence of element, not necessarily visibility for this method
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element at xpath %s not present after %s seconds.", xpath, timeOutSeconds);
            throw e;
        }
    }

    public static void scrollToWebElement(String xpath) {
        logger.info("****************** Scroll to web element at xpath %s", xpath);
        JavascriptExecutor js;
        try {
            js = ((JavascriptExecutor) getDriver());
            WebElement element;
            // Wait for element to be present before trying to scroll to it
            element = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for element %s to be present before scrolling.", xpath);
            throw e;
        }
    }

    public static void waitUntilVisibilityOfElementLocated(By locator, int seconds) {
        logger.info("****************** Wait until visibility of element located by %s, and seconds: %s", locator, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverInitializationException e) {
            logger.error("WebDriver not initialized while waiting for visibility of %s.", locator, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element was not visible after waiting for %s seconds. Locator: %s", seconds, locator);
            throw e;
        }
    }

    public static void waitUntilElementAttributeGetChanged(WebElement element, String attribute, String attributeUpdatedValue, int seconds) {
        logger.info("****************** Wait until element attribute %s changes to %s", attribute, attributeUpdatedValue);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.until((ExpectedCondition<Boolean>) driver -> {
                assert driver != null;
                String attributeValue;
                try {
                    // Re-fetch element inside the condition to avoid staleness if possible, though element reference is passed.
                    // For robustness, passing a By locator might be better for attribute checks over time.
                    attributeValue = element.getAttribute(attribute);
                } catch (StaleElementReferenceException e) {
                    logger.warn("StaleElementReferenceException while checking attribute %s for element. Retrying might be needed or re-locate element.", attribute);
                    return false; // Or re-locate and check
                }
                return attributeUpdatedValue.equals(attributeValue);
            });
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element attribute '%s' did not change to '%s' after %s seconds. Element: %s", attribute, attributeUpdatedValue, seconds, element.toString());
            throw e;
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
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)); // presenceOfAllElementsLocatedBy or presenceOfElementLocated
            // If the above doesn't throw TimeoutException, the element(s) is/are present.
            // No explicit assertion needed here as wait.until itself will throw if condition not met.
            logger.info("Element %s is present.", locator);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element %s was NOT present after %s seconds.", locator, timeOutSeconds);
            throw e; // Re-throw for test framework to catch
        }
    }

    // mouse hover and release webelement
    public static void mouseHoverAndRelease(WebElement element, WebElement subElement) {
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_CLICKABLE_TIMEOUT", "30"));
        waitUntilElementClickable(element, defaultTimeout); // Ensure main element is interactable
        Actions actions;
        try {
            actions = new Actions(getDriver());
            actions.moveToElement(element).perform();
            // Ensure sub-element is also interactable/visible before trying to move to it
            waitUntilElementClickable(subElement, defaultTimeout);
            actions.moveToElement(subElement).release().build().perform();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementRefreshedAndClickable(WebElement element, int timeOutSeconds) {
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element not clickable even after refreshing: %s", element.toString());
            throw e;
        }
    }

    public static void waitUntilElementClickable(By locator, int timeoutInSeconds) {
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element at locator %s was not clickable after %s seconds.", locator, timeoutInSeconds);
            throw e;
        }
    }

    public static void mouseHoverAndClick(By mainLocator, By subLocator) {
        // Defaulting to a reasonable timeout.
        int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_VISIBLE_TIMEOUT", "60"));
        WebDriverWait localWait;
        try {
            localWait = new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }

        WebElement mainLink = localWait.until(ExpectedConditions.visibilityOfElementLocated(mainLocator));
        // waitUntilElementPresent(mainLink, defaultTimeout); // Already covered by visibilityOfElementLocated

        Actions actions;
        try {
            actions = new Actions(getDriver());
            actions.moveToElement(mainLink).perform();

            WebElement subMenu = localWait.until(ExpectedConditions.elementToBeClickable(subLocator)); // Wait for sub-menu to be clickable
            // genericWait(300); // Avoid fixed waits if possible; elementToBeClickable is better.

            // No need to moveToElement again if elementToBeClickable passed for subMenu,
            // as it implies it's visible and interactable.
            actions.click(subMenu).build().perform();
            BasePage.waitUntilPageCompletelyLoaded();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout during mouse hover and click. Main: %s, Sub: %s", mainLocator, subLocator);
            throw e;
        }
    }

    public static void waitUntilPresenceOfElementLocated(By locator, int timeoutSeconds) {
        logger.info("****************** Wait until element present: %s", locator);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (WebDriverInitializationException e) {
            logger.error("WebDriver not initialized while waiting for presence of %s", locator, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element was not present after waiting for %s seconds. Locator: %s", timeoutSeconds, locator);
            throw e;
        }
    }


    public static void waitUntilDisabledAttributeDisappears(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until 'disabled' attribute disappears from element: %s", element);

        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(driver -> {
                try {
                    // Re-fetch the element's disabled attribute in each check to get the current state
                    return element.getAttribute("disabled") == null;
                } catch (StaleElementReferenceException e) {
                    // If element is stale, it means the DOM changed, re-evaluation will happen.
                    // Or, you might need to re-find the element if its locator is available.
                    logger.warn("StaleElementReferenceException while checking 'disabled' attribute for %s. Condition will re-evaluate.", element);
                    return false; // Let the wait continue and re-evaluate
                }
            });
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("'disabled' attribute did not disappear from element %s after %s seconds.", element.toString(), timeOutSeconds);
            throw e;
        }
    }

    public static WebElement getElement(String xpath) {
        try {
            // It's better to wait for presence before returning
            int defaultTimeout = Integer.parseInt(getProperty("ELEMENT_PRESENT_TIMEOUT", "10"));
            return new WebDriverWait(getDriver(), Duration.ofSeconds(defaultTimeout))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        } catch (WebDriverInitializationException e) {
            // This custom exception might hide the actual Selenium exception (e.g. TimeoutException)
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Element with xpath '%s' not found after timeout.", xpath);
            throw new NoSuchElementException("Element not found with xpath: " + xpath, e);
        }
    }

    public static void waitUntilAttributeContains(WebElement element, String attributeName, String attributeValue, int timeoutSeconds) {
        logger.info("****************** Wait until element attribute '%s' contains '%s'", attributeName, attributeValue);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        } catch (WebDriverInitializationException e) {
            logger.error("WebDriver not initialized while waiting for attribute '%s' to contain '%s' on element %s", attributeName, attributeValue, element, e);
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Attribute '%s' did not contain '%s' for element %s after %s seconds.", attributeName, attributeValue, element.toString(), timeoutSeconds);
            throw e;
        }
    }

    /**
     * Waits until a specific value is loaded in a dropdown (without opening it)
     * @param dropdown The WebElement representing the dropdown
     * @param expectedValue The expected value to wait for
     * @param timeoutInSeconds Maximum time to wait
     */
    public static void waitUntilValueLoadedInDropdown(WebElement dropdown, String expectedValue, int timeoutInSeconds) {
        logger.info("Waiting for value '%s' to be loaded in dropdown: %s", expectedValue, dropdown);

        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    try {
                        String currentValue = dropdown.getText().trim();
                        logger.debug("Current dropdown value: %s", currentValue);
                        return currentValue.equals(expectedValue);
                    } catch (StaleElementReferenceException e) {
                        logger.debug("Stale element exception while waiting for dropdown value, retrying...");
                        return false;
                    } catch (Exception e) {
                        logger.debug("Exception while checking dropdown value: %s", e.getMessage());
                        return false;
                    }
                }

                @Override
                public String toString() {
                    return String.format("value to be '%s' in dropdown (%s)", expectedValue, dropdown);
                }
            });
            logger.info("Dropdown now contains the expected value: %s", expectedValue);
        } catch (WebDriverInitializationException e) {
            logger.error("Error while waiting for dropdown value: %s", e.getMessage());
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for value '%s' in dropdown %s", expectedValue, dropdown.toString());
            throw e;
        }
    }

    // navigate to method
    public static void navigateTo(String url) {
        logger.info("****************** Navigate to: %s", url);
        try {
            getDriver().navigate().to(url);
        } catch (WebDriverInitializationException e) {
            logger.error("Error while navigating to %s: %s", url, e.getMessage());
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitForDropdownTextChange(By dropdownLocator, String initialText, int timeoutInSeconds){
        logger.info("Waiting for dropdown text at %s to change from '%s'", dropdownLocator, initialText);
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(dropdownLocator, initialText)));
            logger.info("Dropdown text at %s has changed from '%s'.", dropdownLocator, initialText);
        } catch (WebDriverInitializationException e) {
            logger.error("** Error while waiting for dropdown value: %s", e.getMessage());
            throw new WebDriverRuntimeException(e);
        } catch (TimeoutException e) {
            logger.error("Timeout: Dropdown text at %s did not change from '%s' within %s seconds.", dropdownLocator, initialText, timeoutInSeconds);
            throw e;
        }
    }

    public static class WebDriverRuntimeException extends RuntimeException {
        public WebDriverRuntimeException(Throwable cause) {
            super(cause);
        }
        public WebDriverRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
