package com.carehires.steps.worker;

import com.carehires.actions.workers.ViewWorkerProfileActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ViewWorkerProfileSteps {

    ViewWorkerProfileActions profileActions = new ViewWorkerProfileActions();

    @Then("^User verifies the worker profile status as (.*)$")
    public void verifyProfileStatus(String status) {
        profileActions.verifyProfileStatus(status);
    }

    @And("User accepts all the compliance")
    public void acceptAllCompliance() {
        profileActions.acceptAllCompliance();
    }

    @And("User updates the worker profile as Submitted for Review")
    public void updateWorkerProfileAsSubmittedForReview() {
        profileActions.updateWorkerProfileAsSubmittedForReview();
    }

    @And("User updates the worker profile as Approve")
    public void updateWorkerProfileAsApprove() {
        profileActions.approveProfile();
    }
}
