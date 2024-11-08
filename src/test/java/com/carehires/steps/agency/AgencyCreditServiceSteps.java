package com.carehires.steps.agency;

import com.carehires.actions.agency.AgencyCreditServiceActions;
import io.cucumber.java.en.And;

public class AgencyCreditServiceSteps {

    AgencyCreditServiceActions creditServiceActions = new AgencyCreditServiceActions();

    @And("User enters valid agency - Credit Service information")
    public void enterCreditServiceInformation() {
        creditServiceActions.enterCreditServiceInformation();
    }

    @And("User moves to Credit Service and edit data")
    public void editCreditServiceData() {
        creditServiceActions.editCreditService();
    }
}
