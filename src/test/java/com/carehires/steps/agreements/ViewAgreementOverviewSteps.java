package com.carehires.steps.agreements;

import com.carehires.actions.agreements.ViewAgreementOverviewActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ViewAgreementOverviewSteps {

    ViewAgreementOverviewActions viewAgreementOverviewActions = new ViewAgreementOverviewActions();

    @Then("^User verifies agreement payment status as (.*) and signature status as (.*)$")
    public void verifyAgreementPaymentStatusAndSignatureStatus(String paymentStatus, String signatureStatus) {
        viewAgreementOverviewActions.verifyAgreementPaymentStatusAndSignatureStatus(paymentStatus, signatureStatus);
    }

    @And("User clicks on Mark as signed button")
    public void clickOnMarkAsSignedButton() {
        viewAgreementOverviewActions.clickOnMarkAsSigned();
    }

    @And("User clicks on Active Agreement button")
    public void clickOnActiveAgreementButton() {
        viewAgreementOverviewActions.clickOnActiveAgreement();
    }

    @Then("^User verifies agreement signature status as (.*)$")
    public void verifyAgreementSignatureStatus(String signatureStatus) {
        viewAgreementOverviewActions.verifyAgreementSignatureStatus(signatureStatus);
    }

    @Then("User verifies available data in the worker rates table")
    public void verifyAvailableDataInTheWorkerRatesTable() {
        viewAgreementOverviewActions.verifyContentsInWorkerRates();
    }
}
