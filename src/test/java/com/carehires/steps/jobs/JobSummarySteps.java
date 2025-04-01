package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobSummaryActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class JobSummarySteps {

    private final JobSummaryActions jobSummaryActions = new JobSummaryActions();

    @Then("User enters Job Summary")
    public void enterJobSummary() {
        jobSummaryActions.enterJobSummary();
    }

    @And("User clicks on Edit button on Job Summary")
    public void clickOnEditButton() {
        jobSummaryActions.clickOnEditButton();
    }

    @And("User verifies shift summary for job post template - scenario1")
    public void verifyValuesForScenario1() {
        jobSummaryActions.verifySummaryForScenario1();
    }

    @Then("User enters Job Summary from template")
    public void enterJobSummaryFromTemplate() {
        jobSummaryActions.enterJobSummaryFromTemplate();
    }
}
