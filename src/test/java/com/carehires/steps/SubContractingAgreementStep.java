package com.carehires.steps;

import com.carehires.actions.agency.SubContractingAgreementActions;
import io.cucumber.java.en.When;

public class SubContractingAgreementStep {

    SubContractingAgreementActions subContract = new SubContractingAgreementActions();

    @When("User clicks on the Invite button on the Sub Contracting Agreement page")
    public void inviteButton() {
        subContract.clickOnInviteButton();
    }

    @When("User clicks on the Complete Profile button on the Sub Contracting Agreement page")
    public void completeProfileButton() {
        subContract.clickOnCompleteProfileButton();
    }
}
