package com.carehires.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.carehires.steps", "com.carehires.hooks"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        tags = "@EditDraftProvider"
)

public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}