package com.carehires.steps.agency;

import com.carehires.actions.agency.SearchAgencyActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class SearchAgencySteps {

    SearchAgencyActions searchAgencyActions = new SearchAgencyActions();

    @When("User finds recently created agency")
    public void findRecentlyCreatedAgency() {
        searchAgencyActions.searchByText();
    }

    @And("User searching an agency which is in Draft stage")
    public void searchingDraftAgency() {
        searchAgencyActions.findDraftAgency();
    }

    @When("User finds recently updated agency")
    public void findRecentlyUpdatedAgency() {
        searchAgencyActions.searchRecentlyUpdatedAgency();
    }
}
