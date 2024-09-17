package com.carehires.steps.agency;

import com.carehires.actions.agency.CreateAgencyCreditServiceActions;
import io.cucumber.java.en.And;

public class CreateAgencyCreaditServiceSteps {

    CreateAgencyCreditServiceActions creditServiceActions = new CreateAgencyCreditServiceActions();

    @And("User enters valid agency - Credit Service information")
    public void enterCreditServiceInformation() {
        creditServiceActions.enterCreditServiceInformation();
    }
}
