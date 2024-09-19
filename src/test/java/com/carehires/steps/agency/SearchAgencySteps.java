package com.carehires.steps.agency;

import com.carehires.actions.agency.SearchAgencyActions;
import io.cucumber.java.en.And;

public class SearchAgencySteps {

    SearchAgencyActions searchAgencyActions = new SearchAgencyActions();

    @And("User finds recently created agency")
    public void userFindsRecentlyCreatedAgency() {
        searchAgencyActions.searchByText();
    }
}
