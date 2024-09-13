package com.carehires.steps;

import com.carehires.actions.LeftSideMenuActions;
import io.cucumber.java.en.And;

public class LeftSideMenuSteps {
    LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();

    @And("^User navigates to Agency Create page$")
    public void moveToAgencyCreatePage() {
        leftSideMenuActions.gotoAgencyCreatePage();
    }
}
