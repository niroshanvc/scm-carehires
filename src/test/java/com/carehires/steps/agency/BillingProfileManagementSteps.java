package com.carehires.steps.agency;

import com.carehires.actions.agency.BillingProfileManagementActions;
import io.cucumber.java.en.And;

public class BillingProfileManagementSteps {

    BillingProfileManagementActions billingActions = new BillingProfileManagementActions();

    @And("^User adds Billing Profile Management data$")
    public void addBillingProfileManagement() {
       billingActions.addBilling();
    }

    @And("User moves to Billing and edit data")
    public void editBillingAddressInformation() {
        billingActions.editBillingInfo();
    }
}
