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

    @And("User enters data to Job Details")
    public void enteringJobDetails() {
        jobDetailsActions.enterJobDetailsInData();
    }

    @And("User edits the Job Details")
    public void editJobDetails() {
        jobDetailsActions.editInfo();
    }

    @And("User enters Job Details for special holiday")
    public void enterJobDetailsForSpecialHoliday() {
        jobDetailsActions.enterJobDetailsForSpecialHoliday();
    }
}
