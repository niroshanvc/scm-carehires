package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerEducationAndTrainingActions;
import io.cucumber.java.en.And;

public class CreateWorkerEducationAndTrainingSteps {

    WorkerEducationAndTrainingActions educationAndTrainingActions = new WorkerEducationAndTrainingActions();

    @And("User enters Education and Training data")
    public void enterEducationAndTraining() {
        educationAndTrainingActions.enterDataForEducationAndTraining();
    }
}