package com.carehires.steps.agreements;

import com.carehires.actions.agreements.SignatoriesActions;
import io.cucumber.java.en.And;

public class SignatoriesSteps {

    SignatoriesActions signatoriesActions = new SignatoriesActions();

    @And("User enters Signatories information")
    public void enterSignatories() {
        signatoriesActions.addSignatoryInfo();
    }

    @And("User enters Signatories information for the agreement")
    public void enterSignatoriesInfo() {
        signatoriesActions.addSignatoryInfoForAgreement();
    }
}
