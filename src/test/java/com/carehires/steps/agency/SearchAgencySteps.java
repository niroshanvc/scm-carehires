package com.carehires.steps.agency;

import com.carehires.actions.agency.SearchAgencyActions;
import io.cucumber.java.en.When;

public class SearchAgencySteps {

    SearchAgencyActions searchAgencyActions = new SearchAgencyActions();

    @When("User finds recently created agency")
    public void userFindsRecentlyCreatedAgency() {
        searchAgencyActions.searchByText();
    }
}
