package com.carehires.steps.worker;

import com.carehires.actions.providers.SearchProviderActions;
import com.carehires.actions.workers.SearchWorkerActions;
import io.cucumber.java.en.When;

public class SearchWorkerSteps {

    SearchWorkerActions searchActions = new SearchWorkerActions();

    @When("User finds recently created worker")
    public void findRecentlyCreatedWorker() {
        searchActions.searchByText();
    }
}
