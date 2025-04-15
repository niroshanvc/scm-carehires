package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobsLandingActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class JobsLandingSteps {
    private final JobsLandingActions jobsLanding = new JobsLandingActions();

    @And("User moves to Post New Job page")
    public void moveToJobPost() {
        jobsLanding.clickOnPostJobButton();
    }

    @And("User creates a job")
    public void createJob() {
        jobsLanding.createNewJob();
    }

    @When("User creates a job for cancellation")
    public void createForCancellation() {
        jobsLanding.createNewJobForCancellation();
    }

    @When("User creates an over-due job for cancellation")
    public void createAnOverDueJobForCancellation() {
        jobsLanding.createNewOverDueJobForCancellation();
    }
}
