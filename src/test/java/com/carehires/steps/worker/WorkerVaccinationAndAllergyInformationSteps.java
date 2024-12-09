package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerVaccinationAndAllergyInformationActions;
import io.cucumber.java.en.And;

public class WorkerVaccinationAndAllergyInformationSteps {

    WorkerVaccinationAndAllergyInformationActions vaccinationAndAllergyInformationActions = new WorkerVaccinationAndAllergyInformationActions();

    @And("User enters Vaccination and Allergy Information data")
    public void enterVaccinationAndAllergyInformationData() {
        vaccinationAndAllergyInformationActions.enterDataForVaccinationInformation();
    }
}