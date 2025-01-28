package com.carehires.steps.agreements;

import com.carehires.actions.agreements.AgreementOverviewActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class AgreementOverviewSteps {

    AgreementOverviewActions agreementOverviewActions = new AgreementOverviewActions();

    @Then("User verifies Total Agreements link")
    public void verifyTotalAgreements() {
        agreementOverviewActions.verifyTotalAgreements();
    }

    @And("User verifies Issue Agreement link")
    public void verifyIssueAgreementLink() {
        agreementOverviewActions.verifyIssueAgreementLink();
    }
}
