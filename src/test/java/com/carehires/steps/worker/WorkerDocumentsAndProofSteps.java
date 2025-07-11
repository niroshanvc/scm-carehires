package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerDocumentsAndProofActions;
import io.cucumber.java.en.And;

public class WorkerDocumentsAndProofSteps {

    WorkerDocumentsAndProofActions documentsAndProofActions = new WorkerDocumentsAndProofActions();

    @And("User enters Document and Proof information")
    public void enterDocumentAndProofInformation() {
        documentsAndProofActions.enterDocumentsAndProofData();
    }

    @And("User moves to Basic Information page again")
    public void moveToBasicInformationPage() {
        documentsAndProofActions.moveToBasicInformationStep();
    }

    @And("User moves to Documents and Proof and edit data")
    public void moveToDocumentsAndProofAndEditData() {
        documentsAndProofActions.updateDocumentsAndProof();
    }

    @And("User enters Document and Proof information for non-British worker")
    public void enterDocumentAndProofForNonBritishWorker() {
        documentsAndProofActions.enterDocumentsAndProofForNonBritishWorker();
    }

    @And("User enters Document and Proof information for non-British Student worker")
    public void uploadDocumentsForNonBritishStudentWorker() {
        documentsAndProofActions.uploadDocumentsForNonBritishStudentWorker();
    }

    @And("User uploads Document and Proof information for non-British worker")
    public void uploadDocumentAndProofForNonBritishWorker() {
        documentsAndProofActions.uploadDocumentAndProofForNonBritishWorker();
    }
}
