package com.carehires.steps.tasks;

import com.carehires.actions.LeftSideMenuActions;
import com.carehires.actions.tasks.PaymentAuthorisationsActions;
import io.cucumber.java.en.And;

public class PaymentAuthorisationsSteps {

    LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();
    PaymentAuthorisationsActions paymentsActions = new PaymentAuthorisationsActions();

    @And("User clicks on Authorise button in the Payment Authorisations page")
    public void clickOnAuthoriseButton() {
        leftSideMenuActions.gotoTasksPaymentAuthorisationsPage();
    }
}
