package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobsLandingActions;
import io.cucumber.java.en.And;

public class JobsLandingSteps {
    private final JobsLandingActions jobsLanding = new JobsLandingActions();

    @And("User moves to Post Job page")
    public void moveToJobPost() {
        jobsLanding.clickOnPostJobButton();
    }

    @And("User finds recently posted job")
    public void userFindsRecentlyPostedJob() {
    }

    @And("User creates a job")
    public void createJob() {
        jobsLanding.createNewJob();
    }
}
