package com.carehires.steps.jobs;

import com.carehires.actions.jobs.JobsListViewActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class JobsListViewSteps {

    private final JobsListViewActions jobsListViewActions = new JobsListViewActions();

    @And("User writes the job id into a text file")
    public void writeJobIdIntoATextFile() {
        jobsListViewActions.writeJobIdToAFile();
    }

    @And("User searches jobs by date range")
    public void searchJobsByDateRange() {
        jobsListViewActions.searchJobByDateRange();
    }

    @And("User searches jobs by provider")
    public void searchJobsByProvider() {
        jobsListViewActions.searchByProviderName();
    }

    @And("^User filters jobs by status as (.*)$")
    public void filterJobsByStatusAs(String status) {
        jobsListViewActions.selectJobByJobStatus(status);
    }

    @And("User suggests a worker")
    public void suggestWorker() {
        jobsListViewActions.suggestAWorkerOnJobDetailsPopup();
    }

    @And("User rejects suggested worker")
    public void rejectSuggestedWorker() {
        jobsListViewActions.rejectSuggestedWorkerOnJobDetailsPopup();
    }

    @And("User selects rejected worker")
    public void selectRejectedWorker() {
        jobsListViewActions.selectRejectedWorkerOnJobDetailsPopup();
    }

    @And("User rejects selected worker")
    public void rejectSelectedWorker() {
        jobsListViewActions.rejectSelectedWorkerOnJobDetailsPopup();
    }

    @And("User fills out the timesheets")
    public void fillOutTimesheets() {
        jobsListViewActions.fillingTimesheets();
    }

    @Then("^User (.*) the timesheet$")
    public void approveOrDisputeTimesheet(String action) {
        jobsListViewActions.approveOrDisputeTimesheet(action);
    }

    @And("User clicks on the Cancel Job menu in job detail page")
    public void cancelJobFromJobDetailPage() {
        jobsListViewActions.cancelJobFromDetailPage();
    }

    @And("User clicks on the Cancel Job menu in job view page")
    public void cancelJobFromJobViewPage() {
        jobsListViewActions.cancelJobFromViewPage();
    }

    @And("User sends a message using CH Admin Note")
    public void sendMessageUsingChAdminNote() {
        jobsListViewActions.chAdminNote();
    }

    @Then("User verifies ch admin note is added successfully")
    public void chAdminNoteAddedSuccessfully() {
        jobsListViewActions.verifyChAdminNoteSaved();
    }

    @And("User modifies the cancellation reason")
    public void modifyCancellationReason() {
        jobsListViewActions.modifyCancellationReason();
    }

    @And("User assign a worker to recently posted job")
    public void assignWorkerToJob() {
        jobsListViewActions.assignWorker();
    }

    @Then("User moves to suggested workers tab and verifies Special Holiday worker rates")
    public void verifySpecialHolidayWorkerRates() {
        jobsListViewActions.moveToSuggestedTabAndVerifySpecialHolidayWorkerRates();
    }

    @When("User clicks on Convert to Open")
    public void clickOnConvertToOpen() {
        jobsListViewActions.clickOnConvertToOpen();
    }

    @Then("User verifies the availability of not allocated text")
    public void verifyAvailabilityOfNotAllocatedText() {
        jobsListViewActions.checkAvailabilityOfNotAllocatedText();
    }

    @And("User selects suggested worker")
    public void selectSuggestedWorker() {
        jobsListViewActions.selectingSuggestedWorkerOnJobDetailsPopup();
    }

    @And("User enters timesheets data")
    public void enterTimesheetsData() {
        jobsListViewActions.enterTimesheet();
    }

    @And("User suggests a worker for sleep in job")
    public void suggestingWorker() {
        jobsListViewActions.suggestWorkerOnJobDetailsPopup();
    }

    @Then("^User (.*) the timesheet entered$")
    public void approveDisputeTimesheet(String action) {
        jobsListViewActions.approvingOrDisputingTimesheet(action);
    }

    @And("User enters timesheets data for resubmit")
    public void enterTimesheetsDataForResubmit() {
        jobsListViewActions.enterTimesheetForResubmit();
    }

    @When("User proceeds with submit timesheet again")
    public void proceedWithSubmitTimesheetAgain() {
        jobsListViewActions.completeResubmission();
    }

    @And("User suggests a worker for general job")
    public void suggestWorkerForGeneralJob() {
        jobsListViewActions.suggestWorkerOnJobDetailsPopup();
    }

    @When("User enters timesheets data for general job")
    public void enterTimesheetsForGeneralJob() {
        jobsListViewActions.enterTimesheetForGeneralJob();
    }

    @And("User completes timesheets entry for general job")
    public void completeTimesheetsEntryForGeneralJob() {
        jobsListViewActions.completeTimesheetEntry();
    }

    @When("User proceeds with submit timesheet for general job")
    public void submitTimesheetForGeneralJob() {
        jobsListViewActions.submitTimesheet();
    }

    @And("User suggests a worker for job cancellation")
    public void suggestWorkerForJobCancellation() {
        jobsListViewActions.suggestWorkerForJobCancellation();
    }

    @And("User clicks on job detail close icon")
    public void closeJobDetailPopup() {
        jobsListViewActions.clickOnJobDetailCloseIcon();
    }

    @And("User enters timesheets data for job cancellation")
    public void entersTimesheetsDataForJobCancellation() {
        jobsListViewActions.enterTimesheetForJobCancellation();
    }

    @And("^User (.*) the timesheet entered for job cancellation$")
    public void clickOnDisputeButton(String action) {
        jobsListViewActions.approveOrDisputeTimesheetForJobCancellation(action);
    }

    @And("User tries to suggesting a worker")
    public void trySuggestingWorker() {
        jobsListViewActions.suggestingWorkerOnJobDetailsPopup();
    }

    @And("User suggests a worker for selected job")
    public void suggestAWorkerForSelectedJob() {
        jobsListViewActions.suggestAWorkerForSelectedJob();
    }
}
