package com.carehires.steps.agency;

import com.carehires.actions.agency.CreateBillingProfileManagementActions;
import io.cucumber.java.en.And;

public class BillingProfileManagementSteps {

    CreateBillingProfileManagementActions billingActions = new CreateBillingProfileManagementActions();

    @And("^User adds Billing Profile Management data$")
    public void addBillingProfileManagement() {
       billingActions.addBilling();
    }
}
