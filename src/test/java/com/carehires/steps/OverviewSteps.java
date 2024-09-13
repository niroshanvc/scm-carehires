package com.carehires.steps;

import com.carehires.actions.OverviewActions;
import io.cucumber.java.en.Then;

public class OverviewSteps {
    OverviewActions overviewActions = new OverviewActions();

    @Then("verifies the title of the landing page")
    public void verifiesTheTitleOfTheLandingPage() {
        overviewActions.waitAndAcceptCookies();
        overviewActions.verifyPageTitle();
    }
}
