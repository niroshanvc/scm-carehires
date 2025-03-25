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

    @And("User enters Job Details without Recurrence and Breaks")
    public void enterJobDetailsWithoutRecurrenceAndBreaks() {
        jobDetailsActions.enterCareProviderDetailsWithPostCustomJob();
        jobDetailsActions.enterJobDurationWithoutRecurrenceAndBreaks();
    }

    @And("User enters Job Details with Recurrence and Breaks")
    public void enterJobDetailsWithRecurrenceAndBreaks() {
        jobDetailsActions.enterJobDetailsWithRecurrenceBreaks();
    }

    @And("User enters Job Details with Recurrence and without Breaks")
    public void enterJobDetailsWithRecurrenceButNoBreaks() {
        jobDetailsActions.enterJobDetailsWithRecurrenceWithoutBreaks();
    }

    @And("User enters Job Details with Recurrence and with Breaks")
    public void enterJobDetailsWithRecurrenceAndWithBreaks() {
        jobDetailsActions.enterJobDurationWithProviderAndDuration();
    }

    @And("^User selects Post using Template and select the template as (.*)$")
    public void selectSavedTemplate(String templateName) {
        jobDetailsActions.selectTemplate(templateName);
    }

    @And("User enters Job Duration by disabling both recurrence and breaks")
    public void enterJobDurationOnly() {
        jobDetailsActions.enterJobDurationByDisablingRecurrenceAndBreaks();
    }

    @And("User enters Job Duration by enabling both recurrence and breaks")
    public void enterJobDurationAndEnablingRecurrenceAndBreaks() {
        jobDetailsActions.enterJobDurationOnlyWithRecurrenceAndWithBreaks();
    }

    @And("User enters Job Duration by enabling recurrence only")
    public void enterJobDurationAndEnablingRecurrence() {
        jobDetailsActions.enterJobDurationAndEnablingRecurrenceOnly();
    }

    @And("User selects job type as Sleep In and proceed with custom job")
    public void userSelectsJobTypeAsSleepInAndProceedWithCustomJobForVacancy() {
        jobDetailsActions.enterSleepInJobDetails();
    }

    @And("User selects job type as Sleep In and proceed with multiple vacancies")
    public void selectJobTypeAsSleepInAndProceedWithMultipleVacancies() {
        jobDetailsActions.sleepInJobWithMultipleVacancies();
    }
}
