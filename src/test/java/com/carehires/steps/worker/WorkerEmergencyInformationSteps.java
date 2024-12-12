package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerEmergencyInformationActions;
import io.cucumber.java.en.And;

public class WorkerEmergencyInformationSteps {

    WorkerEmergencyInformationActions emergencyInformationActions = new WorkerEmergencyInformationActions();

    @And("User enters Emergency Information data")
    public void enterEmergencyInformation() {
        emergencyInformationActions.enterDataForEmergencyInformation();
    }

    @And("User moves to Emergency and edit data")
    public void editEmergencyInfo() {
        emergencyInformationActions.updateEmergency();
    }
}