package com.carehires.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.carehires.steps", "com.carehires.hooks"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true,
        tags = "@EditDraftProvider"
//        tags = "@CreateAgency_WithOneLocation or @EditAgencyInDraft or @CreateProviderAndApprovingBySuperAdmin or @EditDraftProvider or @CreateWorker or @EditWorkerProfile"
)

public class CucumberRunnerTests extends AbstractTestNGCucumberTests {

}