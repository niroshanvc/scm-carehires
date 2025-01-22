package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobPreferencesActions;
import io.cucumber.java.en.And;

public class JobPreferencesSteps {

    private final JobPreferencesActions jobPreferencesActions = new JobPreferencesActions();

    @And("User enters Job Preferences")
    public void enterJobPreferences() {
        jobPreferencesActions.enterJobPreferences();
    }
}
