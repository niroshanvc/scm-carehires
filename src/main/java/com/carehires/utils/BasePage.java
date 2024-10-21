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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class BasePage {
    private static BasePage basePage;
    private static org.openqa.selenium.WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getFormatterLogger(BasePage.class);
    static Properties prop;

    private BasePage() {
        String browser = getProperty("BROWSER");

        // Log system environment variables for debugging in CI
        String jenkinsUrl = System.getenv("JENKINS_URL");
        String ciEnvironment = System.getenv("CI");
        logger.info("****************** JENKINS_URL: %s", jenkinsUrl != null ? jenkinsUrl : "Not Set");
        logger.info("****************** CI: %s", ciEnvironment != null ? ciEnvironment : "Not Set");

        // Detect if running in CI (Jenkins or CircleCI)
        boolean isCIRunning = ciEnvironment != null || jenkinsUrl != null;
        logger.info("****************** Running in CI environment: %s", isCIRunning);

        logger.info("****************** Initializing WebDriver for browser: %s", browser);
        try {
            if (browser.equalsIgnoreCase("Chrome")) {
                logger.info("****************** Setting up ChromeDriver.");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("safebrowsing.enabled", true);
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();

                // CI-specific options
                if (isCIRunning) {
                    logger.info("****************** Running in CI environment, configuring Chrome headless options.");
                    options.addArguments("enable-automation");
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                    options.setExperimentalOption("prefs", prefs);
                }

                String opString = options.toString();
                logger.info("****************** Starting ChromeDriver with options: %s", opString);
                driver = new ChromeDriver(options);
                logger.info("****************** ChromeDriver started successfully.");

            } else if (browser.equalsIgnoreCase("Firefox")) {
                logger.info("****************** Setting up FirefoxDriver.");
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();

                if (isCIRunning) {
                    logger.info("****************** Running in CI environment, configuring Firefox headless options.");
                    options.addArguments("--headless");
                }

                driver = new FirefoxDriver(options);
                logger.info("****************** FirefoxDriver started successfully.");
            } else {
                logger.error("****************** Invalid browser specified: %s", browser);
            }

            if (driver != null) {
                driver.manage().window().maximize();
                logger.info("****************** Browser window maximized.");
            }
        } catch (Exception e) {
            logger.error("****************** Error occurred during WebDriver initialization: ", e);
        }

    }

    public static String getProperty(String key) {
        String path = System.getProperty("user.dir") + "/src/test/resources/properties/project.properties";
        try (FileInputStream fs = new FileInputStream(path)) {
            prop = new Properties();
            prop.load(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

    public static void navigate(String urlKey) {
        String url = getProperty(urlKey);
        logger.info("****************** Navigating to %s", url);
        driver.get(url);
    }

    public static void waitUntilElementPresent(WebElement element, int timeOutSeconds) {
        logger.info("****************** Wait until element present: %s, in seconds: %s", element, timeOutSeconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void clickAfterWait(WebElement element) {
        waitUntilElementPresent(element, 60);
        logger.info("****************** Clicked on the web element %s", element);
        element.click();
    }

    public static void scrollToWebElement(WebElement ele) {
        logger.info("****************** Scroll to web element %s", ele);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    public static void clickWithJavaScript(WebElement ele) {
        logger.info("****************** Clicking on the web element captured using webelement: %s", ele);
        waitUntilElementPresent(ele, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", ele);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setUpDriver() {
        if (basePage == null) {
            basePage = new BasePage();
        }
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        basePage = null;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));

        try {
            // Wait for element to be visible
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            logger.error("Element was not clickable after waiting for %s seconds. Locator: %s", timeOutSeconds, element);
            throw e;  // Re-throw the exception after logging
        }
    }

    public static void selectFirstOption(String xpath) {
        Select sel = new Select(driver.findElement(By.xpath(xpath)));
        sel.selectByValue("0");
    }

    public static void clickShiftAndTabKeyTogether(WebElement element) {
        logger.info("****************** Click shift + tab key in %s", element);
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.SHIFT)
                .sendKeys(Keys.TAB)
                .keyUp(Keys.SHIFT)
                .build()
                .perform();
    }

    public static void refreshPage() {
        logger.info("****************** Refreshing page");
        driver.navigate().refresh();
    }

    public static void clearTexts(WebElement element) {
        logger.info("****************** Clear texts in %s", element);
        element.clear();
    }

    public static void moveToBottomOfThePage() {
        logger.info("****************** Move to bottom of the page.");
        JavascriptExecutor js = (JavascriptExecutor) driver;
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
        Actions actions = new Actions(driver);
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
        return driver.getTitle();
    }

    public static void waitUntilPageCompletelyLoaded() {
        logger.info("****************** Wait until page completely loaded");
        for (int i = 1; i < 60; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            boolean ready = Objects.requireNonNull(js.executeScript("return document.readyState"))
                    .toString().equalsIgnoreCase("complete");
            if (ready) {
                return;
            }
        }
    }

    public static void mouseHoverAndClick(WebElement element, WebElement subElement) {
        waitUntilElementClickable(element, 30);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).moveToElement(subElement).click().build().perform();
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
        WebElement ele = driver.findElement(By.xpath(xpath));
        waitUntilElementPresent(ele, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", ele);
    }

    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
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
        WebElement element = driver.findElement(By.xpath(xpath));
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

        try {
            // Wait for element to be visible
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            logger.error("Element was not visible after waiting for %s seconds. Locator: %s", seconds, element);
            throw e;  // Re-throw the exception after logging
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
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public static void waitUntilElementDisappeared(WebElement element, int seconds) {
        logger.info("****************** Wait until element disappeared: %s, and seconds: %s", element, seconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitAndIgnoreStaleException(WebElement element, int seconds) {
        logger.info("****************** Wait until element displayed by ignoring stale element exception: %s, " +
                "and seconds: %s", element, seconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
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
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static void waitUntilElementPresent(String xpath, int timeOutSeconds) {
        logger.info("****************** Wait until element present at xpath: %s, in seconds: %s", xpath, timeOutSeconds);
        WebElement element = driver.findElement(By.xpath(xpath));
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void scrollToWebElement(String xpath) {
        logger.info("****************** Scroll to web element at xpath %s", xpath);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        WebElement element = driver.findElement(By.xpath(xpath));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitUntilVisibilityOfElementLocated(By locator, int seconds) {
        logger.info("****************** Wait until visibility of element located by %s, and seconds: %s", locator, seconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element was not visible after waiting for %s seconds. Locator: %s", seconds, locator);
        }
    }

    public static void waitUntilElementAttributeGetChanged(WebElement element, String attribute, String attributeUpdatedValue, int seconds) {
        logger.info("****************** Wait until element attribute get changed: %s, and seconds: %s", element, seconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        try {
            wait.until((ExpectedCondition<Boolean>) driver -> {
                String attributeValue = element.getAttribute(attribute);
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));

        // Wait until at least one element is present
        wait.until((ExpectedCondition<Boolean>) driver -> {
            assert driver != null;
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty(); // Return true if at least one element is found
        });

        // After waiting, assert that the element is present
        List<WebElement> elements = driver.findElements(locator);
        MatcherAssert.assertThat("Element should be present", elements.size(), Matchers.greaterThan(0));
    }
}
