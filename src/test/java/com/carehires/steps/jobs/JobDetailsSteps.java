package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobDetailsActions;
import io.cucumber.java.en.And;

public class JobDetailsSteps {

    private final JobDetailsActions jobDetailsActions = new JobDetailsActions();

    @And("User enters Job Details")
    public void enterJobDetails() {
        jobDetailsActions.enteringJobDetails();
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

    @And("User enters Job Details for end to end testing")
    public void enterJobDetailsForEnd2EndTesting() {
        jobDetailsActions.enterJobDetailsForEnd2End();
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

    @And("^User selects Post using Template and select the sleep in template as (.*)$")
    public void selectSleepInTemplate(String templateName) {
        jobDetailsActions.selectSleepInTemplate(templateName);
    }

    @And("User enters sleep in duration and recurrence")
    public void enterSleepInDurationAndRecurrence() {
        jobDetailsActions.enterSleepInDurationAndRecurrence();
    }

    @And("User selects more than one vacancy and enters sleep in duration and recurrence data")
    public void enterMoreThanOneVacancyAndSleepInInfo() {
        jobDetailsActions.enterSleepInDurationWithMoreThanOneVacancy();
    }

    @And("User enters Job Details without enabling Recurrence and Breaks")
    public void disablingRecurrenceAndBreaks() {
        jobDetailsActions.disablingRecurrenceAndBreaks();
    }

    @And("User enters general job details")
    public void enterGeneralJobDetails() {
        jobDetailsActions.enterGeneralJobDetails();
    }

    @And("User selects job type as Sleep In and proceed with Custom Job")
    public void proceedWithSleepInAndCustomJob() {
        jobDetailsActions.jobTypeSleepInAndCustomJob();
    }

    @And("User enters job type as Sleep In and Custom Job")
    public void enterJobTypeAsSleepInAndCustomJob() {
        jobDetailsActions.enterJobDetailsForReSubmit();
    }

    @And("User selects job type as General and proceed with Custom Job")
    public void selectGeneralAndProceedWithCustomJob() {
        jobDetailsActions.generalJobAndCustomJob();
    }

    @And("User enters job type as General and Custom Job")
    public void userEntersJobTypeAsGeneralAndCustomJob() {
        jobDetailsActions.enterJobDetailsForManageTimesheet();
    }

    @And("User enters Job Details to manage job template")
    public void enterJobDetailsToManageTemplate() {
        jobDetailsActions.enterJobDetailsToManageTemplate();
    }

    @And("Provider User enters Job Details")
    public void providerUserEntersJobDetails() {
        jobDetailsActions.providerUserEntersJobDetails();
    }

    @And("Provider User enters Job Details with Breaks")
    public void providerUserEntersJobDetailsWithBreaks() {
        jobDetailsActions.providerUserEntersJobDetailsWithBreaks();
    }

    @And("Provider User enters Job Details without Recurrence and Breaks")
    public void providerUserEntersJobDetailsWithoutRecurrenceAndBreaks() {
        jobDetailsActions.providerEnterJobDetailsWithoutRecurrenceAndBreaks();
    }

    @And("^Provider User selects Post using Template and select the template as (.*)$")
    public void selectTemplateByProviderUser(String templateName) {
        jobDetailsActions.providerUserSelectTemplate(templateName);
    }

    @And("Provider User selects job type as Sleep In and proceed with custom job")
    public void providerUserSelectsJobTypeAsSleepInAndProceedWithCustomJob() {
        jobDetailsActions.providerUserSelectJobTypeAsSleepInAndProceedWithCustomJob();
    }

    @And("Provider User enters sleep in duration and recurrence")
    public void providerUserEntersSleepInDurationAndRecurrence() {
        jobDetailsActions.providerUserEntersSleepInDurationAndRecurrence();
    }
}
