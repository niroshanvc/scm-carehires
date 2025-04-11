package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobPreferencesActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

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

    @And("User enters Job Preferences with Job note and enabling block booking")
    public void enterWithJobNoteAndEnablingBlockBooking() {
        jobPreferencesActions.enterJobPreferencesWithJobNoteAndEnablingBlockBooking();
    }

    @When("User enters Job Preferences with skills and without Job note and disabling block booking")
    public void enterPreferencesWithSkillsAndWithoutJobNoteAndDisablingBlockBooking() {
        jobPreferencesActions.enterWithSkillsAndWithoutJobNoteAndDisablingBlockBooking();
    }

    @When("User enters Job Preferences with skills and enabling block booking but no job notes")
    public void enterPreferencesWithSkillsAndBlockBookingsButNoNote() {
        jobPreferencesActions.enterWithSkillsAndEnablingBlockBookingButNoNote();
    }

    @And("User clicks on Continue button on Job Preferences page")
    public void clickContinueButton() {
        jobPreferencesActions.clickContinueButton();
    }

    @And("User clicks on Continue button by removing Job Note")
    public void clickOnContinueButton() {
        jobPreferencesActions.clickContinueButtonAndRemovingJobNote();
    }

    @When("User enters Job Preferences without Job note and disable block booking")
    public void proceedWithoutNoteAndDisablingBlockBooking() {
        jobPreferencesActions.noNoteAndDisablingBlockBooking();
    }

    @And("User enters Job Preferences without Job note")
    public void proceedWithoutJobNote() {
        jobPreferencesActions.clearJobNote();
    }

    @And("User enters Job Preferences without Job note and disabling block booking")
    public void enterWithoutJobNoteAndDisablingBlockBooking() {
        jobPreferencesActions.customJobNoNoteAndDisablingBlockBooking();
    }

    @When("User selects skills without Job note and disabling block booking")
    public void selectSkillsWithoutJobNoteAndDisablingBlockBooking() {
        jobPreferencesActions.selectSkillsWithoutBlockBookingAndNote();
    }

    @When("User selects skills without Job note and enabling block booking")
    public void selectSkillsWithoutJobNoteAndEnablingBlockBooking() {
        jobPreferencesActions.selectSkillsWithBlockBookingAndNote();
    }

    @When("User selects gender and clicks on Continue button")
    public void selectGenderAndClickContinue() {
        jobPreferencesActions.selectGenderAndClickContinue();
    }

    @When("User clears Job note and disabling block booking")
    public void clearJobNoteAndDisablingBlockBooking() {
        jobPreferencesActions.clearJobNoteAndDisableBlockBooking();
    }

    @When("User clears Job note and enabling block booking")
    public void clearJobNoteAndEnablingBlockBooking() {
        jobPreferencesActions.clearJobNoteAndEnableBlockBooking();
    }

    @And("enters job note and click on continue button")
    public void enterJobNoteAndClickOnContinueButton() {
        jobPreferencesActions.continueWithJobNote();
    }

    @When("User selects skills with Job note and enabling block booking")
    public void selectSkillsWithJobNoteAndEnablingBlockBooking() {
        jobPreferencesActions.continueWithEveryOptions();
    }

    @And("User enters skills with block booking but no note")
    public void enterSkillsWithBlockBookingButNoNote() {
        jobPreferencesActions.blockBookingWithSkills();
    }

    @When("User enters Job Preferences without reason and internal notes")
    public void enterPreferencesWithoutReasonAndInternalNotes() {
        jobPreferencesActions.noReasonNoInternalNotes();
    }

    @When("User enters Job Preferences with reason and internal notes")
    public void enterJobPreferencesWithReasonAndInternalNotes() {
        jobPreferencesActions.withReasonAndInternalNotes();
    }

    @When("User enters Job Preferences with both reason and internal notes")
    public void enterPreferencesWithBothReasonAndInternalNotes() {
        jobPreferencesActions.withBothReasonAndInternalNotes();
    }

    @And("User enters Job Preferences for sleep in job")
    public void enterJobPreferencesForSleepInJob() {
        jobPreferencesActions.enterJobPreferencesForSleepIn();
    }

    @And("User enters Job Preferences for general job")
    public void generalJobPreferences() {
        jobPreferencesActions.generalJobPreferences();
    }
}
