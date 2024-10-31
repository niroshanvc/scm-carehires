package com.carehires.steps.worker;

import com.carehires.actions.workers.CreateWorkerDocumentsAndProofActions;
import io.cucumber.java.en.And;

public class CreateWorkerDocumentsAndProofSteps {

    CreateWorkerDocumentsAndProofActions documentsAndProofActions = new CreateWorkerDocumentsAndProofActions();

    @And("User enters Document and Proof information")
    public void enterDocumentAndProofInformation() {
        documentsAndProofActions.enterDocumentsAndProofData();
    }
}
