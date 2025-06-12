package com.carehires.steps.overview;

import com.carehires.actions.overview.ProviderOverviewActions;
import io.cucumber.java.en.Then;

public class ProviderOverviewSteps {

    private final ProviderOverviewActions providerOverview = new ProviderOverviewActions();

    @Then("verifies links are working as expected")
    public void verifyLinksAreWorking() {
        providerOverview.verifyLinksAreWorking();
    }
}
