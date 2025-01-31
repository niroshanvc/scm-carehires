package com.carehires.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.carehires.steps", "com.carehires.hooks"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true,
        tags = "@CreateJob"
//        tags = "@CreateAgency or @EditAgency
//        or @CreateProvider or @CreateProviderWithCustomBilling or @EditProvider
//        or @CreateWorker or @EditWorker"
//        or @CreateAgreement or @CreateAgreementForDDProvider or @ViewAgreement, @EditAgreement, @OverviewAgreement
//        or @CreateJob or @EditJob or @ViewJob
)

public class CucumberRunnerTests extends AbstractTestNGCucumberTests {

}