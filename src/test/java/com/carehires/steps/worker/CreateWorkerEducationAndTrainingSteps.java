package com.carehires.steps.worker;

import com.carehires.actions.workers.CreateWorkerEducationAndTrainingActions;
import io.cucumber.java.en.And;

public class CreateWorkerEducationAndTrainingSteps {

    CreateWorkerEducationAndTrainingActions educationAndTrainingActions = new CreateWorkerEducationAndTrainingActions();

    @And("User enters Education and Training data")
    public void enterEducationAndTraining() {
        educationAndTrainingActions.enterDataForEducationAndTraining();
    }
}
