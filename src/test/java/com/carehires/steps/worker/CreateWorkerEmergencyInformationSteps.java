package com.carehires.steps.worker;

import com.carehires.actions.workers.CreateWorkerEmergencyInformationActions;
import io.cucumber.java.en.And;

public class CreateWorkerEmergencyInformationSteps {

    CreateWorkerEmergencyInformationActions emergencyInformationActions = new CreateWorkerEmergencyInformationActions();

    @And("User enters Emergency Information data")
    public void enterEmergencyInformation() {
        emergencyInformationActions.enterDataForEmergencyInformation();
    }
}