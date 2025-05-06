package com.carehires.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.carehires.steps", "com.carehires.hooks"},
        plugin = { "json:target/cucumber.json", // Generates the JSON report
                "pretty",
                "html:target/cucumber-html-reports"},
        monochrome = true,
        tags = "@ApproveGeneral" // Default constant value
        //        tags = "CreateAgency, EditAgency
//        or CreateProvider, CreateProviderWithCustomBilling, EditProvider
//        or CreateWorker, EditWorker"
//        or CreateAgreement, CreateAgreementForDDProvider, ViewAgreement, EditAgreement, OverviewAgreement
//        or CreateJobPost, CreateJobPostWithBreaks, CreateJobPostForSpecialHoliday
//        EditJobPost, ViewJob, CancelJobFromJobDetail,
//        or CancelJobFromJobView, ManageAllocations
//        or JobsAgencyView
//        VerifyJobPostForSpecialHoliday --> health check annotation
//        PostGeneralJobsCustomJobs
)

public class CucumberRunnerTests extends AbstractTestNGCucumberTests {

}