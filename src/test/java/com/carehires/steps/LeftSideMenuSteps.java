package com.carehires.steps;

import com.carehires.actions.LeftSideMenuActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class LeftSideMenuSteps {
    LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();

    @When("^User navigates to Agency Create page$")
    public void moveToAgencyCreatePage() {
        leftSideMenuActions.gotoAgencyCreatePage();
    }

    @And("^User navigates to Agency View page$")
    public void moveToAgencyViewPage() {
        leftSideMenuActions.gotoAgencyViewPage();
    }

    @When("^User navigates to Worker Create page$")
    public void moveToWorkerCreatePage() {
        leftSideMenuActions.gotoWorkerCreatePage();
    }

    @When("^User navigates to Worker View page$")
    public void moveToWorkerViewPage() {
        leftSideMenuActions.gotoWorkerViewPage();
    }

    @When("User navigates to Provider Create page")
    public void moveToProviderCreatePage() {
        leftSideMenuActions.gotoProviderCreatePage();
    }

    @And("^User navigates to Provider View page$")
    public void moveToProviderViewPage() {
        leftSideMenuActions.gotoProviderViewPage();
    }

    @When("User navigates to Agreement Create page")
    public void moveToAgreementCreatePage() {
        leftSideMenuActions.gotoAgreementCreatePage();
    }
}