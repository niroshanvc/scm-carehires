package com.carehires.steps.worker;

import com.carehires.actions.workers.CreateWorkerBasicInformationActions;
import io.cucumber.java.en.And;

public class CreateWorkerBasicInformationSteps {

    CreateWorkerBasicInformationActions basicInformationActions = new CreateWorkerBasicInformationActions();

    @And("User enters valid worker - basic information")
    public void enterBasicInformation() {
        basicInformationActions.enterWorkerBasicInformationData();
    }
}
