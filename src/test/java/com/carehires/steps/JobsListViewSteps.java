package com.carehires.steps;

import com.carehires.actions.jobs.JobsListViewActions;
import io.cucumber.java.en.And;

public class JobsListViewSteps {

    private final JobsListViewActions jobsListViewActions = new JobsListViewActions();

    @And("User writes the job id into a text file")
    public void writeJobIdIntoATextFile() {
        jobsListViewActions.writeJobIdToAFile();
    }
}
