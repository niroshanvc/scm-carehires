package com.carehires.hooks;

import com.carehires.common.GlobalVariables;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) throws BasePage.WebDriverInitializationException {
        BasePage.setUpDriver();

        // Determine the entity type (agency, provider, etc.) based on scenario tags
        String entityType = determineEntityTypeFromScenario(scenario);

        // Get the current increment value from the DataConfigurationReader
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(entityType);

        // Store the increment value in GlobalVariables for use across steps
        GlobalVariables.storeIncrementedValue(entityType, incrementValue);
        logger.info("Storing increment value in GlobalVariables: {}", incrementValue);
    }

    @After
    public void tearDown(Scenario scenario) throws BasePage.WebDriverInitializationException {
        final byte[] screenshot = ((TakesScreenshot) BasePage.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
        BasePage.tearDown();

        // This will run after each scenario
        if (scenario.isFailed()) {
            logger.info("Test failed. No increment value will be updated.");
        }
    }

    // Determine the entity type based on the scenario tags
    private String determineEntityTypeFromScenario(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@Provider")) {
            return "provider";
        } else if (scenario.getSourceTagNames().contains("@Agency")) {
            return "agency";
        } else if (scenario.getSourceTagNames().contains("@Worker")) {
            return "worker";
        } else if (scenario.getSourceTagNames().contains("@Agreement")) {
            return "agreement";
        } else if (scenario.getSourceTagNames().contains("@Job")) {
            return "job";
        }
        // If no matching entity type is found, log the issue and return "default"
        logger.error("Unknown entity type for scenario: {}", scenario.getName());
        return "default";
    }
}