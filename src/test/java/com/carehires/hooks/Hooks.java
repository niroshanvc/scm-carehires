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
}
