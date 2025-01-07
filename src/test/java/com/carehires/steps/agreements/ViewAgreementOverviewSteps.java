package com.carehires.steps.agreements;

import com.carehires.actions.agreements.ViewAgreementOverviewActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ViewAgreementOverviewSteps {

    ViewAgreementOverviewActions viewAgreementOverviewActions = new ViewAgreementOverviewActions();

    @Then("User verifies agreement payment status and signature status")
    public void verifyAgreementPaymentStatusAndSignatureStatus() {
        viewAgreementOverviewActions.verifyAgreementPaymentStatusAndSignatureStatus();
    }

    @And("User clicks on Mark as signed button")
    public void clickOnMarkAsSignedButton() {
        viewAgreementOverviewActions.clickOnMarkAsSigned();
    }
}
