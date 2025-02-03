package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobDetailsActions;
import io.cucumber.java.en.And;

public class JobDetailsSteps {

    private final JobDetailsActions jobDetailsActions = new JobDetailsActions();

    @And("User enters Job Details")
    public void enterJobDetails() {
        jobDetailsActions.enterJobDetails();
    }

    @And("User enters Job Details with Breaks")
    public void enterJobDetailsWithBreaks() {
        jobDetailsActions.enterJobDetailsWithBreaks();
    }
}
