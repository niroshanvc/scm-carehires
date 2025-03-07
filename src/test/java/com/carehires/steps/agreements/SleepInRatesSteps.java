package com.carehires.steps.agreements;

import com.carehires.actions.agreements.SleepInRatesActions;
import io.cucumber.java.en.And;

public class SleepInRatesSteps {

    SleepInRatesActions sleepInRates = new SleepInRatesActions();

    @And("User enters Sleep In Rates and verify calculations")
    public void enterSleepInRatesAndVerifyCalculations() {
        sleepInRates.addSleepInRatesAndVerifyCalculations();
    }

    @And("User enters Sleep In Rates")
    public void enterSleepInRates() {
        sleepInRates.addSleepInRates();
    }
}
