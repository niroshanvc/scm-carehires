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
}
