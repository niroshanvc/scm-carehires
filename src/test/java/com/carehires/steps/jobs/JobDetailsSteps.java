package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobDetailsActions;
import io.cucumber.java.en_scouse.An;

public class JobDetailsSteps {

    private final JobDetailsActions jobDetailsActions = new JobDetailsActions();

    @An("User enters Job Details")
    public void enterJobDetails() {
        jobDetailsActions.enterJobDetails();
    }
}
