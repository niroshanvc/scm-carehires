package com.carehires.steps.agreements;

import com.carehires.actions.agreements.SearchAgreementsActions;
import io.cucumber.java.en.And;

public class SearchAgreementsSteps {

    SearchAgreementsActions searchAgreementsActions = new SearchAgreementsActions();

    @And("User searches previously created agreement")
    public void searchAgreement() {
        searchAgreementsActions.searchAnAgreementUsingAdvanceFilters();
    }
}
