package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobsLandingActions;
import io.cucumber.java.en.And;

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
}
