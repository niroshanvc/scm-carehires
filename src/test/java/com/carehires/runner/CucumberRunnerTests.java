package com.carehires.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.carehires.steps", "com.carehires.hooks"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true,
        tags = "@CreateAgency"
//        tags = "@CreateAgency or @EditAgency or @CreateProvider or @EditProvider or @CreateWorker or @EditWorker"
//        or @CreateAgreement
)

public class CucumberRunnerTests extends AbstractTestNGCucumberTests {

}