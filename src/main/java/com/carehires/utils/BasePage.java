package com.carehires.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class BasePage {
    private static BasePage basePage;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getFormatterLogger();
    static Properties prop;

    private BasePage() {
        String browser = getProperty("BROWSER");
        logger.info("Open browser: %s", browser);

        if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().clearDriverCache().setup();
            WebDriverManager.chromedriver().clearResolutionCache().setup();
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        } else {
            logger.info("Browser name %s is invalid.", browser);
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
        logger.info("Navigating to %s", url);
        driver.get(url);
    }

    public static void waitUntilElementPresent(WebElement element, int timeOutSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void clickAfterWait(WebElement element) {
        waitUntilElementPresent(element, 60);
        logger.info("---Clicked on the web element %s", element);
        element.click();
    }

    public static void scrollToWebElement(WebElement ele) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    public static void clickWithJavaScript(WebElement ele) {
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
        } catch (Exception ex) {
            logger.error("Object not found: %s", xpath);
        }
        return els;
    }

    public static void typeWithStringBuilder(WebElement element, String data) {
        logger.info("Type using StringBuilder in %s", element);
        StringBuilder sb = new StringBuilder(data);
        element.sendKeys(sb);
    }

    public static String getText(WebElement element) {
        logger.info("Get text in %s", element);
        return element.getText();
    }

    public static String getAttributeValue(WebElement element, String attribute) {
        logger.info("Get attribute value from %s and %s", element, attribute);
        return element.getAttribute(attribute);
    }

    public static void clickTabKey(WebElement element) {
        logger.info("Click tab key in %s", element);
        element.sendKeys(Keys.TAB);
    }

    public static void waitUntilElementClickable(WebElement element, int timeOutSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void selectFirstOption(String xpath) {
        Select sel = new Select(driver.findElement(By.xpath(xpath)));
        sel.selectByValue("0");
    }

    public static void clickShiftAndTabKeyTogether(WebElement element) {
        logger.info("Click shift + tab key in %s", element);
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.SHIFT)
                .sendKeys(Keys.TAB)
                .keyUp(Keys.SHIFT)
                .build()
                .perform();
    }

    public static void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    public static void clearTexts(WebElement element) {
        logger.info("Clear texts in %s", element);
        element.clear();
    }

    public static void moveToBottomOfThePage() {
        logger.info("Move to bottom of the page.");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void waitFor(int milliseconds) {
        logger.info("Wait for %s milliseconds", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollDownUntilVisible(WebElement element) {
        logger.info("Scroll down until visible: %s", element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToBottomOfPage() {
        logger.info("Scroll down to bottom of the page.");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.END).perform();
    }

    public static void clearAndEnterTexts(WebElement element, String texts) {
        logger.info("Clear and enter texts in %s", element);
        element.clear();
        typeWithStringBuilder(element, texts);
    }

    public static String getPageTitle() {
        logger.info("Getting actual page title");
        return driver.getTitle();
    }

    public static void waitUntilPageCompletelyLoaded() {
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
        Actions actions = new Actions(driver);
        actions.moveToElement(element).moveToElement(subElement).click().build().perform();
    }
}
