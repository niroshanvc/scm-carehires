package com.carehires.steps.worker;

import com.carehires.actions.workers.CreateWorkerVaccinationAndAllergyInformationActions;
import io.cucumber.java.en.And;

public class CreateWorkerVaccinationAndAllergyInformationSteps {

    CreateWorkerVaccinationAndAllergyInformationActions vaccinationAndAllergyInformationActions = new CreateWorkerVaccinationAndAllergyInformationActions();

    @And("User enters Vaccination and Allergy Information data")
    public void enterVaccinationAndAllergyInformationData() {
        vaccinationAndAllergyInformationActions.enterDataForVaccinationInformation();
    }
}