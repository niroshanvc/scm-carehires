package com.carehires.steps.agreements;

import com.carehires.actions.agreements.CancellationPolicyActions;
import io.cucumber.java.en.And;

public class CancellationPolicySteps {
    CancellationPolicyActions cancellationPolicy = new CancellationPolicyActions();

    @And("User enters Cancellation Policy and verify calculations")
    public void enterCancellationPolicyAndVerifyCalculations() {
        cancellationPolicy.addCancellationPolicyAndVerifyCalculations();
    }

    @And("User enters Cancellation Policy")
    public void enterCancellationPolicy() {
        cancellationPolicy.addCancellationPolicy();
    }
}
