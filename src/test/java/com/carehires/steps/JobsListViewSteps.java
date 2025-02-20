package com.carehires.steps;

import com.carehires.actions.jobs.JobsListViewActions;
import io.cucumber.java.en.And;

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
}
