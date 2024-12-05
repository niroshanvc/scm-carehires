package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerBasicInformationActions;
import io.cucumber.java.en.And;

public class CreateWorkerBasicInformationSteps {

    WorkerBasicInformationActions basicInformationActions = new WorkerBasicInformationActions();

    @And("User enters valid worker - basic information")
    public void enterBasicInformation() {
        basicInformationActions.enterWorkerBasicInformationData();
    }

    @And("User creates a worker in draft stage")
    public void createWorkerInDraft() {
        basicInformationActions.createWorkerInDraftStage();
    }

    @And("User edit worker profile data")
    public void editWorkerProfileData() {
        basicInformationActions.updateWorkerBasicInfo();
    }
}
