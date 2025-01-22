package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobsLandingActions;
import io.cucumber.java.en.And;

public class JobsLandingSteps {
    private final JobsLandingActions jobsLanding = new JobsLandingActions();

    @And("User moves to Post Job page")
    public void moveToJobPost() {
        jobsLanding.clickOnPostJobButton();
    }
}
