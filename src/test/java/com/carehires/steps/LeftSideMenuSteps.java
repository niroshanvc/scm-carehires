package com.carehires.steps;

import com.carehires.actions.LeftSideMenuActions;
import io.cucumber.java.en.And;

public class LeftSideMenuSteps {
    LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();

    @And("^User navigates to Agency Create page$")
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

}