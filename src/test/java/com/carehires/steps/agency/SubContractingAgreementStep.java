package com.carehires.steps.agency;

import com.carehires.actions.agency.SubContractingAgreementActions;
import io.cucumber.java.en.And;
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

    @And("User moves to Agreement and completes the profile")
    public void moveToAgreementAndCompletesTheProfile() {
        subContract.moveToAgreementAndCompleteTheProfile();
    }

    @And("User downloads the manually signed agreement")
    public void downloadAgreement() {
        subContract.downloadAndDeleteAgreement();
    }
}
