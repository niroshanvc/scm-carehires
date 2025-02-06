package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobSummaryActions;
import io.cucumber.java.en.And;

public class JobSummarySteps {

    private final JobSummaryActions jobSummaryActions = new JobSummaryActions();

    @And("User enters Job Summary")
    public void enterJobSummary() {
        jobSummaryActions.enterJobSummary();
    }

    @And("User clicks on Edit button on Job Summary")
    public void clickOnEditButton() {
        jobSummaryActions.clickOnEditButton();
    }
}
