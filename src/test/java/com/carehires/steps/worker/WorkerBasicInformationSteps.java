package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerBasicInformationActions;
import io.cucumber.java.en.And;

public class WorkerBasicInformationSteps {

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

    @And("User collects workerId from Basic Information page")
    public void getAutoGeneratedWorkerId() {
        basicInformationActions.getGeneratedWorkerId();
    }

    @And("User creates a worker")
    public void createWorker() {
        basicInformationActions.completeCreateWorkerProcess();
    }

    @And("User enters valid non-British worker basic information")
    public void enteringNonBritishWorkerBasicInfo() {
        basicInformationActions.enterNonBritishWorkerBasicInformation();
    }

    @And("User enters valid non-British worker with basic information")
    public void enterNonBritishWorkerWithBasicInformation() {
        basicInformationActions.enterNonBritishWorkerWithBasicInformation();
    }

    @And("User enters valid non-British worker and related basic information")
    public void enterValidNonBritishWorkerBasicInformation() {
        basicInformationActions.enterValidNonBritishWorkerBasicInformation();
    }

    @And("User enters valid non-British worker details by selecting Graduate Settlement as Visa Type")
    public void createWorkerBySelectingGraduateSettlement() {
        basicInformationActions.createWorkerBySelectingGraduateSettlementVisaType();
    }

    @And("User creates a non-British worker in draft stage")
    public void createANonBritishWorkerInDraftStage() {
        basicInformationActions.createNonBritishWorkerInDraftStage();
    }

    @And("User edit non-British worker profile data")
    public void updateNonBritishWorkerProfileData() {
        basicInformationActions.updateNonBritishWorkerBasicInfo();
    }
}
