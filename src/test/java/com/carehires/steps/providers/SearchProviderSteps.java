package com.carehires.steps.providers;

import com.carehires.actions.providers.SearchProviderActions;
import io.cucumber.java.en.When;

public class SearchProviderSteps {

    SearchProviderActions searchActions = new SearchProviderActions();

    @When("User finds recently created provider")
    public void findRecentlyCreatedProvider() {
        searchActions.searchByText();
    }
}
