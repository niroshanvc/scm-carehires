package com.carehires.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
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
import java.util.Set;
import java.util.function.Supplier;

public class BasePage {
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getFormatterLogger(BasePage.class);
    private static final Properties prop = new Properties();
    private static FileInputStream fis;
    private static final String HEADLESS = "--headless";
    private static final String DISABLE_GPU = "--disable-gpu";
    private static final String NO_SANDBOX = "--no-sandbox";
    private static final String DISABLE_DEV = "--disable-dev-shm-usage";
    private static final String JAVASCRIPT_CLICK = "arguments[0].click();";
    private static final String READONLY = "readonly";

    //for parallel execution
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private static final String RESOURCES_DIR = System.getProperty("user.dir") + File.separator + "src" + File.
            separator + "test" + File.separator + "resources";

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

    public static void waitUntil(Supplier<Boolean> condition, int timeoutInSeconds) {
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.jsReturnsValue("return (" + condition.
                get() + ");")));
    }

    // close newly opened tab window and switch back to the main window
    public static void closeCurrentTabAndSwitchToMainWindow() {
        logger.info("****************** Closing current tab and switching back to main window.");
        try {
            String mainWindowHandle = getDriver().getWindowHandle();
            for (String windowHandle : getDriver().getWindowHandles()) {
                if (!windowHandle.equals(mainWindowHandle)) {
                    getDriver().switchTo().window(windowHandle).close();
                }
            }
            getDriver().switchTo().window(mainWindowHandle);
        } catch (NoSuchElementException | StaleElementReferenceException | WebDriverInitializationException e) {
            logger.error("Error while closing the current tab and switching back to the main window: %s",
                    e.getMessage());
        }
    }

    /**
     * Switches the WebDriver focus to the other open tab.
     * This method assumes there are exactly two tabs open.
     * If you are on Tab A, it will switch to Tab B, and vice versa.
     */
    public static void switchToOtherTab() {
        try {
            WebDriver driver = getDriver();
            String currentHandle = driver.getWindowHandle();
            Set<String> allHandles = driver.getWindowHandles();

            if (allHandles.size() != 2) {
                logger.warn("switchToOtherTab() was called, but there are not exactly two tabs open. Found: " +
                        "%s", allHandles.size());
                return;
            }

            for (String handle : allHandles) {
                if (!handle.equalsIgnoreCase(currentHandle)) {
                    driver.switchTo().window(handle);
                    logger.info("Switched to tab with handle: %s", handle);
                    break;
                }
            }
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
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
        options.addArguments("--safeBrowse-disable-download-protection"); // Moved common argument out of the conditional

        if (isCIRunning) {
            // For CI, use a fixed size and other headless-friendly options.
            logger.info("CI environment detected. Using fixed window size and headless mode.");
            options.addArguments(
                    HEADLESS,
                    "--window-size=1920,1080",
                    DISABLE_GPU,
                    NO_SANDBOX,
                    DISABLE_DEV
            );
            // The configureCIOptions(options) call might be redundant if you add arguments here,
            // but can be kept if it does other things.
        } else {
            // For local execution, use --start-maximized to fill the screen.
            logger.info("Local environment detected. Using --start-maximized.");
            options.addArguments("--start-maximized");
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
            chromeOptions.addArguments(HEADLESS, DISABLE_GPU, NO_SANDBOX, DISABLE_DEV);
        } else if (options instanceof FirefoxOptions firefoxOptions) {
            firefoxOptions.addArguments(HEADLESS);
        } else if (options instanceof EdgeOptions edgeOptions) {
            edgeOptions.addArguments(HEADLESS, DISABLE_GPU, NO_SANDBOX, DISABLE_DEV);
        } else {
            logger.warn("Unsupported browser options class: %s", options.getClass().getSimpleName());
        }
    }

    // Static initializer block to load properties
    static {
        try (FileInputStream fis = new FileInputStream(RESOURCES_DIR + File.separator + "properties" + File.separator + "project.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            logger.error("An error occurred: ", e);
            throw new WebDriverRuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("An error occurred: ", e);
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
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementPresent(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element present: %s, in seconds: %s", element, timeOutSeconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
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
        JavascriptExecutor js;
        try {
            js = ((JavascriptExecutor) getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    public static void clickWithJavaScript(WebElement ele) {
        logger.info("****************** Clicking on the web element captured using webelement: %s", ele);
        waitUntilElementPresent(ele, 30);
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

    public static void sendKeys(WebElement element, String data) {
        logger.info("****************** Send keys in %s", element);
        element.sendKeys(data);
    }

    public static void sendKeys(By by, String data) {
        logger.info("****************** Type using by locator in %s", by);
        WebElement element;
        try {
            element = getDriver().findElement(by);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        element.clear();
        element.sendKeys(data);
    }

    public static String getText(WebElement element) {
        logger.info("****************** Get text in %s", element);
        waitUntilElementPresent(element, 30);
        return element.getText();
    }

    public static String getTextWithoutWait(WebElement element) {
        logger.info("****************** Without waiting any second, get text in %s", element);
        return element.getText();
    }

    public static String getAttributeValue(WebElement element, String attribute) {
        logger.info("****************** attribute value from element %s and %s", element, attribute);
        waitUntilElementPresent(element, 30);
        return element.getAttribute(attribute);
    }

    public static String getAttributeValue(String xpath, String attribute) {
        logger.info("****************** attribute value from xpath %s and attribute %s", xpath, attribute);
        By by = By.xpath(xpath);
        WebElement ele;
        try {
            ele = getDriver().findElement(by);
            waitUntilElementPresent(ele, 30);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        return ele.getAttribute(attribute);
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

    public static void waitUntilElementClickableMethod(WebElement element, int timeOutSeconds) {
        logger.info("****************** Waiting until element is clickable: %s", element);
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element is now clickable: %s", element);
        } catch (TimeoutException e) {
            logger.error("TimeoutException: Element was not clickable after waiting for %s seconds. Element: " +
                    "%s", timeOutSeconds, elementToString(element), e);
            // Optionally re-throw or handle as a test failure
            throw e; // It's often better to let the test fail clearly if a wait times out
        } catch (InvalidElementStateException ies) {
            logger.error("InvalidElementStateException during wait for clickability. Element: %s . " +
                    "Exception: %s", elementToString(element), ies);
            throw ies;
        }
    }

    // Helper to get some identifying information from the WebElement for logging
    public static String elementToString(WebElement element) {
        if (element == null) {
            return "null";
        }
        try {
            // Attempt to get some useful identifying information
            String tagName = element.getTagName();
            String text = element.getText();
            String id = element.getAttribute("id");
            String name = element.getAttribute("name");
            String classAttr = element.getAttribute("class");

            StringBuilder sb = new StringBuilder();
            sb.append("TagName: '").append(tagName).append("'");
            if (id != null && !id.isEmpty()) sb.append(", Id: '").append(id).append("'");
            if (name != null && !name.isEmpty()) sb.append(", Name: '").append(name).append("'");
            if (classAttr != null && !classAttr.isEmpty()) sb.append(", Class: '").append(classAttr).append("'");
            if (!text.isEmpty() && text.length() < 50) sb.append(", Text: '").append(text.trim()).append("'");
            // Add other attributes if they are commonly used in your application for identification
            return sb.toString();
        } catch (Exception e) {
            // If trying to get attributes fails (e.g., stale element), return a basic string
            return element.toString();
        }
    }

    public static void clickElement(WebElement element, int timeOutSeconds) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        waitUntilElementClickable(element, timeOutSeconds); // Wait for it to be clickable
        try {
            logger.info("Attempting to click element: %s", elementToString(element));
            element.click();
            logger.info("Successfully clicked element: %s", elementToString(element));
        } catch (InvalidElementStateException e) {
            logger.error("InvalidElementStateException when trying to click. Element: %s. IsDisplayed: %s, " +
                            "IsEnabled: %s",
                    elementToString(element), element.isDisplayed(), element.isEnabled(), e);
            throw e;
        } catch (Exception e) { // Catch other potential exceptions during click
            logger.error("Exception during click on element: %s . Exception: %s", elementToString(element), e);
            throw e;
        }
    }

    public static void selectFirstOption(String xpath) {
        Select sel;
        try {
            sel = new Select(getDriver().findElement(By.xpath(xpath)));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        sel.selectByValue("0");
    }

    public static void clickShiftAndTabKeyTogether(WebElement element) {
        logger.info("****************** Click shift + tab key in %s", element);
        Actions actions;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        actions.keyDown(Keys.SHIFT)
                .sendKeys(Keys.TAB)
                .keyUp(Keys.SHIFT)
                .build()
                .perform();
    }

    // double-click on an element
    public static void doubleClick(WebElement element) {
        Actions actions;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        actions.doubleClick(element).perform();
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
        element.clear();
    }

    public static void clearTextsUsingSendKeys(WebElement element) {
        logger.info("****************** Clear texts in %s using Send Keys", element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }


    public static void moveToBottomOfThePage() {
        logger.info("****************** Move to bottom of the page.");
        JavascriptExecutor js;
        try {
            js = (JavascriptExecutor) getDriver();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToBottomOfPage() {
        logger.info("****************** Scroll down to bottom of the page.");
        Actions actions;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        actions.sendKeys(Keys.END).perform();
    }

    public static void clearAndEnterTexts(WebElement element, String texts) {
        logger.info("****************** Clear and enter texts in %s", element);
        waitUntilElementPresent(element, 30);
        element.clear();
        typeWithStringBuilder(element, texts);
    }

    // Java
    public static void clearFirstAndEnterTexts(WebElement element, String texts, int timeOutSeconds) {
        logger.info("******************** Attempting to clear and enter texts in: %s",
                elementToString(element));

        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.visibilityOf(element),
                    ExpectedConditions.elementToBeClickable(element)
            ));

            String readOnly = element.getAttribute(READONLY);
            logger.info("Element %s is visible and enabled. IsDisplayed: %s, IsEnabled: %s, ReadOnly: %s. " +
                            "Attempting to clear...",
                    elementToString(element), element.isDisplayed(), element.isEnabled(), readOnly);

            if (readOnly == null || readOnly.equalsIgnoreCase("false")) {
                element.clear();
                logger.info("Successfully cleared element: %s", elementToString(element));
                typeWithStringBuilder(element, texts);
            } else {
                logger.warn("Element %s is readonly. Skipping clear and type.", elementToString(element));
            }

        } catch (TimeoutException e) {
            logger.error("TimeoutException: Element was not ready for clear/sendkeys after waiting for %s " +
                            "seconds. Element: %s",
                    timeOutSeconds, elementToString(element), e);
            try {
                logger.error("Current state before throw - IsDisplayed: %s, IsEnabled: %s, TagName: %s, " +
                                "ReadOnly: %s",
                        element.isDisplayed(), element.isEnabled(), element.getTagName(), element.getAttribute(
                                READONLY));
            } catch (Exception ex) {
                logger.error("Could not get element state during TimeoutException logging", ex);
            }
            throw e;
        } catch (InvalidElementStateException ies) {
            logger.error("InvalidElementStateException during clear or type. Element: %s. IsDisplayed: %s, " +
                            "IsEnabled: %s, TagName: %s, ReadOnly: %s",
                    elementToString(element), element.isDisplayed(), element.isEnabled(), element.getTagName(),
                    element.getAttribute(READONLY), ies);
            throw ies;
        } catch (Exception exc) {
            logger.error("An unexpected error occurred in clearFirstAndEnterTexts for element: %s . " +
                    "Exception: %s", elementToString(element), exc);
        }
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
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(90));
            // Wait until the document's readyState is "complete"
            ExpectedCondition<Boolean> pageLoadCondition = driver -> {
                assert driver != null;
                try {
                    return Objects.equals(((JavascriptExecutor) getDriver()).executeScript("return document." +
                            "readyState"), "complete");
                } catch (WebDriverInitializationException e) {
                    throw new WebDriverRuntimeException(e);
                }
            };
            wait.until(pageLoadCondition);
        } catch (TimeoutException timeoutException) {
            logger.error("Page is not completely loaded after 90 seconds");
            throw timeoutException;
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
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
            logger.error("Failed to perform mouse hover and click: message: %s and error: %s",
                    e.getMessage(), e);
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
        WebElement ele;
        try {
            ele = getDriver().findElement(By.xpath(xpath));
            waitUntilElementPresent(ele, 30);
            JavascriptExecutor js;
            js = (JavascriptExecutor) getDriver();
            js.executeScript(JAVASCRIPT_CLICK, ele);

        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void clickWithJavaScript(By locator) {
        logger.info("****************** Clicking on the web element captured using locator: %s", locator);
        WebElement ele;
        try {
            ele = getDriver().findElement(locator);
            waitUntilElementClickable(ele, 30);
            JavascriptExecutor js;
            js = (JavascriptExecutor) getDriver();
            js.executeScript(JAVASCRIPT_CLICK, ele);

        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
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
        element.sendKeys(Keys.ENTER);
    }

    public static void typeWithStringBuilderAndDelay(WebElement element, String data, int milliseconds) {
        logger.info("****************** Type using StringBuilder in %s with %d milliseconds delay", element, milliseconds);
        StringBuilder sb = new StringBuilder(data);
        waitUntilElementPresent(element, 30);
        String[] strs = sb.toString().split("");
        for (String character : strs) {
            element.sendKeys(character);
            genericWait(milliseconds);
        }
    }

    public static void waitUntilElementClickable(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element clickable at xpath: %s", xpath);
        WebElement element;
        try {
            element = getDriver().findElement(By.xpath(xpath));
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean isElementDisplayed(WebElement element) {
        logger.info("****************** Checking element - %s is displayed:", element);
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(String xpath) {
        logger.info("******************** Checking element - %s is displayed:", xpath);
        try {
            WebElement element = getDriver().findElement(By.xpath(xpath));
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementDisplayed(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed: %s, and seconds: %s", element, seconds);

        // Create a wait instance with the provided timeout
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            logger.error("Element was not displayed after waiting for %s seconds. Locator: %s",
                    seconds, element);
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

    public static List<WebElement> listOfWebElements(String xpath) {
        List<WebElement> els = null;
        try {
            els = getDriver().findElements(By.xpath(xpath));
        } catch (Exception ex) {
            logger.error("******************* Object not found at the locator: %s", xpath);
        }
        return els;
    }

    public static void mouseHoverOverElement(WebElement element) {
        waitUntilElementClickable(element, 30);
        Actions actions;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        actions.moveToElement(element).build().perform();
    }

    public static void waitUntilElementDisappeared(WebElement element, int seconds) {
        logger.info("****************** Wait until element disappeared: %s, and seconds: %s", element,
                seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitAndIgnoreStaleException(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed by ignoring stale element " +
                "exception: %s, " + "and seconds: %s", element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
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
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void waitUntilElementPresent(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element present at xpath: %s, in seconds: %s",
                xpath, timeOutSeconds);
        WebElement element;
        try {
            element = getDriver().findElement(By.xpath(xpath));
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void scrollToWebElement(String xpath) {
        logger.info("****************** Scroll to web element at xpath %s", xpath);
        JavascriptExecutor js;
        try {
            js = ((JavascriptExecutor) getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        WebElement element;
        try {
            element = getDriver().findElement(By.xpath(xpath));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitUntilVisibilityOfElementLocated(By locator, int seconds) {
        logger.info("****************** Wait until visibility of element located by %s, and seconds: %s",
                locator, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element was not visible after waiting for %s seconds. Locator: %s", seconds, locator);
        }
    }

    public static void waitUntilElementAttributeGetChanged(WebElement element, String attribute, String
            attributeUpdatedValue, int seconds) {
        logger.info("****************** Wait until element attribute get changed: %s, and seconds: %s",
                element, seconds);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        try {
            wait.until((ExpectedCondition<Boolean>) driver -> {
                assert driver != null;
                String attributeValue;
                try {
                    attributeValue = Objects.requireNonNull(((JavascriptExecutor) getDriver()).executeScript
                            ("return arguments[0].getAttribute(arguments[1]);", element, attribute)).toString();
                } catch (WebDriverInitializationException e) {
                    throw new WebDriverRuntimeException(e);
                }
                assert attributeValue != null;
                return attributeValue.equals(attributeUpdatedValue);
            });
        } catch (TimeoutException e) {
            logger.error("Element attribute was not changed after waiting for %s seconds. Locator: %s",
                    seconds, element);
        }
    }

    /**
     * Verifies if an element is present on the page after waiting for a specified time using Hamcrest assertion.
     *
     * @param locator        The By locator for the element.
     * @param timeOutSeconds The number of seconds to wait for the element to be present.
     */
    public static void verifyElementIsPresentAfterWait(By locator, int timeOutSeconds) {
        logger.info("****************** Verify element present: %s, in seconds: %s", locator, timeOutSeconds);

        // Create a WebDriverWait instance
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }

        // Wait until at least one element is present
        wait.until((ExpectedCondition<Boolean>) driver -> {
            assert driver != null;
            List<WebElement> elements;
            try {
                elements = getDriver().findElements(locator);
            } catch (WebDriverInitializationException e) {
                throw new WebDriverRuntimeException(e);
            }
            return !elements.isEmpty(); // Return true if at least one element is found
        });

        // After waiting, assert that the element is present
        List<WebElement> elements;
        try {
            elements = getDriver().findElements(locator);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    // mouse hover and release webelement
    public static void mouseHoverAndRelease(WebElement element, WebElement subElement) {
        waitUntilElementClickable(element, 30);
        Actions actions;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        actions.moveToElement(element);
        actions.moveToElement(subElement);
        actions.release().build().perform();
    }

    public static void waitUntilElementRefreshedAndClickable(WebElement element, int timeOutSeconds) {
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
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
            throw new WebDriverRuntimeException(e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void mouseHoverAndClick(By mainLocator, By subLocator) {
        WebElement mainLink = wait.until(ExpectedConditions.visibilityOfElementLocated(mainLocator));
        waitUntilElementPresent(mainLink, 60);
        Actions actions;
        try {
            actions = new Actions(getDriver());
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        actions.moveToElement(mainLink).perform();
        WebElement subMenu = wait.until(ExpectedConditions.presenceOfElementLocated(subLocator));
        genericWait(300);
        actions.moveToElement(subMenu);
        waitUntilElementClickable(subMenu, 60);
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
            throw new WebDriverRuntimeException(e);
        }
        wait.until(driver -> {
            try {
                try {
                    return Objects.requireNonNull(((JavascriptExecutor) getDriver()).executeScript(
                            "return arguments[0].getAttribute('disabled') == null;", element));
                } catch (WebDriverInitializationException e) {
                    throw new WebDriverRuntimeException(e);
                }
            } catch (RuntimeException e) {
                throw new WebDriverRuntimeException(e);
            }
        });
    }

    public static WebElement getElement(String xpath) {
        try {
            return getDriver().findElement(By.xpath(xpath));
        } catch (WebDriverInitializationException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static void waitUntilAttributeContains(WebElement element, String attributeName, String attributeValue, int timeoutSeconds) {
        logger.info("****************** Wait until element attribute contains: %s", attributeValue);
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));

            // Wait until the 'attributeName' attribute contains 'attributeValue'
            wait.until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        } catch (WebDriverInitializationException e) {
            logger.error("Element attribute not get updated %s", attributeValue);
        }
    }

    /**
     * Waits until a specific value is loaded in a dropdown (without opening it)
     *
     * @param dropdown         The WebElement representing the dropdown
     * @param expectedValue    The expected value to wait for
     * @param timeoutInSeconds Maximum time to wait
     */
    public static void waitUntilValueLoadedInDropdown(WebElement dropdown, String expectedValue, int timeoutInSeconds) {
        logger.info("Waiting for value %s to be loaded in dropdown", expectedValue);

        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        } catch (WebDriverInitializationException e) {
            logger.error("Error while waiting for dropdown value: %s", e.getMessage());
        }

        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    // Get the current selected value without opening the dropdown
                    String currentValue = dropdown.getText().trim();
                    logger.debug("Current dropdown value: %s", currentValue);

                    // Check if it matches the expected value
                    return currentValue.equals(expectedValue);
                } catch (StaleElementReferenceException e) {
                    // Handle case where element is being updated in DOM
                    logger.debug("Stale element exception while waiting for dropdown value, retrying...");
                    return false;
                } catch (Exception e) {
                    logger.debug("Exception while checking dropdown value: %s", e.getMessage());
                    return false;
                }
            }

            @Override
            public String toString() {
                return "value to be '" + expectedValue + "' in dropdown";
            }
        });

        logger.info("Dropdown now contains the expected value: %s", expectedValue);
    }

    // navigate to method
    public static void navigateTo(String url) {
        logger.info("****************** Navigate to: %s", url);
        try {
            getDriver().navigate().to(url);
        } catch (WebDriverInitializationException e) {
            logger.error("Error while navigating to %s: %s", url, e.getMessage());
        }
    }

    public static void waitForDropdownTextChange(By dropdownLocator, int timeoutInSeconds) {
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(dropdownLocator,
                    "Select Site")));
        } catch (WebDriverInitializationException e) {
            logger.error("** Error while waiting for dropdown value: %s", e.getMessage());
        }
    }

    public static class WebDriverRuntimeException extends RuntimeException {
        public WebDriverRuntimeException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Waits until an element is visible on the page.
     *
     * @param locator The By locator of the element.
     * @return The WebElement once it's visible.
     */
    public static WebElement waitUntilElementVisible(By locator) {
        logger.info("Waiting for %s to be visible: ", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until an already located element is visible on the page.
     *
     * @param element The WebElement.
     * @return The WebElement once it's visible.
     */
    public static WebElement waitUntilElementVisible(WebElement element) {
        // First, ensure the element reference itself isn't null before trying to use it
        if (element == null) {
            throw new IllegalArgumentException("WebElement to wait for cannot be null");
        }
        // It's good practice to log which element you're waiting for.
        // Getting a good string representation of a WebElement can be tricky if it becomes stale.
        logger.info("Waiting for pre-located element to be visible: %s", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static String getValueFromDisabledInputViaJS(WebElement element) {
        if (element == null) {
            logger.error("Element is null, cannot get value via JS.");
            return null;
        }
        JavascriptExecutor js;
        try {
            js = (JavascriptExecutor) getDriver();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        try {
            // Try to get the 'value' property directly using JavaScript
            Object result = js.executeScript("return arguments[0].value;", element);
            return result != null ? result.toString() : null;
        } catch (Exception e) {
            logger.error("Error getting value via JS from element: %s", elementToString(element));
            return null;
        }
    }

    public static WebElement waitUntilElementPresent(By locator, int timeoutInSeconds) {
        if (locator == null) {
            throw new IllegalArgumentException("Locator to wait for cannot be null");
        }
        try {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
        logger.info("Waiting for element to be present in DOM: %s", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Selenium click method without waiting
    public static void clickWithoutWaiting(WebElement element) {
        logger.info("****************** Clicking on the web element without waiting: %s", element);
        element.click();
    }

    /**
     * Clicks an element that opens a new tab, switches focus to the new tab,
     * and waits for a key element on the new page to be visible.
     *
     * @param elementToClick     The WebElement that, when clicked, opens the new tab.
     * @param keyElementOnNewTab A By locator for a reliable element on the new page
     *                           (e.g., a header or main content area) to wait for.
     * @return The String handle of the original tab, so you can switch back later.
     */
    public static String switchToNewTabAndWait(WebElement elementToClick, By keyElementOnNewTab) {
        WebDriver driver;
        String originalTabHandle;
        try {
            driver = getDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10-second timeout

            // 1. Store the handle of the original tab
            originalTabHandle = driver.getWindowHandle();
            logger.info("Original tab handle: %s", originalTabHandle);

            // 2. Click the link to open the new tab
            elementToClick.click();

            // 3. Wait until the number of window handles is 2
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            logger.info("New tab detected.");

            // 4. Get all window handles and find the new one
            Set<String> allWindowHandles = driver.getWindowHandles();
            String newTabHandle = "";
            for (String handle : allWindowHandles) {
                if (!handle.equalsIgnoreCase(originalTabHandle)) {
                    newTabHandle = handle;
                    break;
                }
            }

            // 5. Switch to the new tab
            if (!newTabHandle.isEmpty()) {
                driver.switchTo().window(newTabHandle);
                logger.info("Switched focus to new tab: %s", newTabHandle);
            } else {
                throw new RuntimeException("Could not find new tab to switch to.");
            }

            // 6. Wait for the new page's key element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(keyElementOnNewTab));
            logger.info("Key element '%s' is visible on the new tab. Page is ready.", keyElementOnNewTab);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }

        // 7. Return the original handle so you can switch back if needed
        return originalTabHandle;
    }

    // switch to the iframe by element
    public static void switchToIframe(WebElement iframeElement) {
        try {
            getDriver().switchTo().frame(iframeElement);
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }

    public static void switchToDefaultContent() {
        try {
            getDriver().switchTo().defaultContent();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverRuntimeException(e);
        }
    }
}
