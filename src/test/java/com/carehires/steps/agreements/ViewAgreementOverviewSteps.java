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

    @And("User clicks on Mark as signed button and do verifications")
    public void clickOnMarkAsSignedButtonAndDoVerifications() {
        viewAgreementOverviewActions.clickOnMarkAsSignedAndDoVerifications();
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
    public void verifyAvailableDataInWorkerRatesTable() {
        viewAgreementOverviewActions.verifyContentsInWorkerRates();
    }

    @Then("User verifies available data in the Sleep in Request table")
    public void verifyAvailableDataInSleepInRequestTable() {
        viewAgreementOverviewActions.verifyContentsInSleepInRequest();
    }

    @And("User makes that agreement inactive")
    public void makeAgreementInactive() {
        viewAgreementOverviewActions.markAsInactive();
    }

    @And("User marks it as active again")
    public void marksItAsActiveAgain() {
        viewAgreementOverviewActions.markAsActiveAgain();
    }

    @And("User edit site")
    public void editSiteInfo() {
        viewAgreementOverviewActions.editSite();
    }

    @And("User remove worker rates")
    public void removeWorkerRates() {
        viewAgreementOverviewActions.removeWorkerRates();
    }

    @And("User edits worker rates")
    public void editWorkerRates() {
        viewAgreementOverviewActions.updateWorkerRates();
    }

    @And("User removes cancellation policy")
    public void removeCancellationPolicy() {
        viewAgreementOverviewActions.removeCancellationPolicy();
    }

    @And("User add new cancellation policy")
    public void addNewCancellationPolicy() {
        viewAgreementOverviewActions.addNewCancellationPolicy();
    }

    @And("User removes sleep in request")
    public void removeSleepInRequest() {
        viewAgreementOverviewActions.removeSleepInRequest();
    }

    @And("User adds sleep in request")
    public void addSleepInRequest() {
        viewAgreementOverviewActions.addSleepInRequest();
    }

    @And("User downloads the manually signed agreement with agency and provider")
    public void downloadAgreement() {
        viewAgreementOverviewActions.downloadAndDeleteAgreement();
    }

    @And("^User write downs (.*) worker rates into a text file$")
    public void writeWorkerRates(String rateType) {
        viewAgreementOverviewActions.writeDownWorkerRates(rateType);
    }

    @And("User moves to worker rates popup")
    public void moveToWorkerRatesPopup() {
        viewAgreementOverviewActions.openWorkerRatesPopup();
    }

    @And("User closes worker rates popup")
    public void closeWorkerRatesPopup() {
        viewAgreementOverviewActions.closeWorkerRatesPopup();
    }

    @Then("User verifies name of the agreement")
    public void verifyNameOfTheAgreement() {
        viewAgreementOverviewActions.verifyNameOfTheAgreement();
    }

    @And("User verifies agreement ids")
    public void verifyAgreementIds() {
        viewAgreementOverviewActions.verifyAgreementIds();
    }

    @And("User verifies saved worker rates can be viewed")
    public void verifyWorkerRatesCanBeViewed() {
        viewAgreementOverviewActions.verifySavedWorkerRatesCanBeViewed();
    }

    @And("User verifies saved sleep in rates can be viewed")
    public void verifySleepInRatesCanBeViewed() {
        viewAgreementOverviewActions.verifySavedSleepInRatesCanBeViewed();
    }
}
