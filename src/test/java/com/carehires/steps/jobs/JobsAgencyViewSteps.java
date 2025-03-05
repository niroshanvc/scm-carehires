package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobsAgencyViewActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class JobsAgencyViewSteps {

    private final JobsAgencyViewActions agencyViewActions = new JobsAgencyViewActions();

    @And("User navigates to Jobs Agency View page")
    public void navigateToAgencyViewPage() {
        agencyViewActions.moveToAgencyView();
    }

    @Then("User views recently created job post")
    public void viewJobPost() {
        agencyViewActions.viewJobPost();
    }

    @And("User Cancels recently posted job")
    public void cancelJob() {
        agencyViewActions.cancelJob();
    }
}
