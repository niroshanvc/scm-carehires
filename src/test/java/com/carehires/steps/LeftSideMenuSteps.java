package com.carehires.steps;

import com.carehires.actions.LeftSideMenuActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class LeftSideMenuSteps {
    LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();

    @Given("^User navigates to Agency Create page$")
    public void moveToAgencyCreatePage() {
        leftSideMenuActions.gotoAgencyCreatePage();
    }

    @And("^User navigates to Agency View page$")
    public void moveToAgencyViewPage() {
        leftSideMenuActions.gotoAgencyViewPage();
    }

    @And("^User navigates to Worker Create page$")
    public void moveToWorkerCreatePage() {
        leftSideMenuActions.gotoWorkerCreatePage();
    }

    @Given("User navigates to Provider Create page")
    public void moveToProviderCreatePage() {
        leftSideMenuActions.gotoProviderCreatePage();
    }

    @And("^User navigates to Provider View page$")
    public void moveToProviderViewPage() {
        leftSideMenuActions.gotoProviderViewPage();
    }
}