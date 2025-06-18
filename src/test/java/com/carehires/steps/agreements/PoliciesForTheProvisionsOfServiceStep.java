package com.carehires.steps.agreements;

import com.carehires.actions.agreements.PoliciesForTheProvisionsOfServiceActions;
import io.cucumber.java.en.And;

public class PoliciesForTheProvisionsOfServiceStep {

    PoliciesForTheProvisionsOfServiceActions policies = new PoliciesForTheProvisionsOfServiceActions();

    @And("User enters Policies for the provisions of service")
    public void enterPoliciesForTheProvisionOfService() {
        policies.enterPolicies();
    }

    @And("User enters Policies for the provisions of service in smoke test")
    public void enterPolicies() {
        policies.enterPoliciesForSmokeTest();
    }
}
