package com.carehires.hooks;

import com.carehires.utils.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public static void setup() {
        BasePage.setUpDriver();
    }

    @After
    public static void tearDown(Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) BasePage.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
        BasePage.tearDown();
    }

    /*public static void initDriver() {
        String browser = System.getProperty("BROWSER");
        if (browser == null) {
            browser = BasePage.getProperty("BROWSER");
        }

        if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            BasePage.driver = new FirefoxDriver();
            BasePage.driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().clearDriverCache().setup();
            WebDriverManager.chromedriver().clearResolutionCache().setup();
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            if (System.getenv("CI") != null) {
                BasePage.logger.info("Running in CircleCI, configuring Chrome for headless execution.");
                options.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
            }

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");

            BasePage.driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            BasePage.driver = new EdgeDriver();
            BasePage.driver.manage().window().maximize();
        } else {
            BasePage.logger.info("Browser name %s is invalid.", browser);
        }
    }*/
}