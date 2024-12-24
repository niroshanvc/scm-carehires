package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerEducationAndTrainingActions;
import io.cucumber.java.en.And;

public class WorkerEducationAndTrainingSteps {

    WorkerEducationAndTrainingActions educationAndTrainingActions = new WorkerEducationAndTrainingActions();

    @And("User enters Education and Training data")
    public void enterEducationAndTraining() {
        educationAndTrainingActions.enterDataForEducationAndTraining();
    }

    @And("User moves to Education and Training and edit data")
    public void editEducationAndTraining() {
        educationAndTrainingActions.updateEducationAndTraining();
    }
}