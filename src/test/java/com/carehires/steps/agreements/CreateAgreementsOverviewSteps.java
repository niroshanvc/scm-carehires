package com.carehires.steps.agreements;

import com.carehires.actions.agreements.CreateAgreementsOverviewActions;
import io.cucumber.java.en.And;

public class CreateAgreementsOverviewSteps {
    CreateAgreementsOverviewActions agreementsOverview = new CreateAgreementsOverviewActions();

    @And("user enters agreement overview data")
    public void enterAgreementOverviewData() {
        agreementsOverview.enterOverviewInfo();
    }
}
