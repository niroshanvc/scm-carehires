package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobPreferencesActions;
import io.cucumber.java.en.And;

public class JobPreferencesSteps {

    private final JobPreferencesActions jobPreferencesActions = new JobPreferencesActions();

    @And("User enters Job Preferences")
    public void enterJobPreferences() {
        jobPreferencesActions.enterJobPreferences();
    }

    @And("User enters Job Preferences and enabling block booking")
    public void enterJobPreferencesByEnablingBlockBooking() {
        jobPreferencesActions.enterJobPreferencesAndEnablingBlockBooking();
    }

    @And("User edits Job Preferences")
    public void editJobPreferences() {
        jobPreferencesActions.editPreferences();
    }

    @And("User enters data to Job Preferences")
    public void enterDataToJobPreferences() {
        jobPreferencesActions.enterJobPreferencesData();
    }
}
