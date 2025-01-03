package com.carehires.steps.agreements;

import com.carehires.actions.agreements.WorkerRatesActions;
import io.cucumber.java.en.And;

public class WorkerRatesSteps {

    WorkerRatesActions workerRates = new WorkerRatesActions();

    @And("User enters Worker Rates")
    public void enterWorkerRates() {
        workerRates.enterWorkerRates();
    }
}
