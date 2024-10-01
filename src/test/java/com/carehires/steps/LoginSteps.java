package com.carehires.steps;

import com.carehires.actions.OverviewActions;
import com.carehires.actions.SignInPageActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginSteps {

    SignInPageActions signInPageActions = new SignInPageActions();
    OverviewActions overviewActions = new OverviewActions();

    @Given("^User navigates to the signin page$")
    public void navigateToSingInPage() {
        signInPageActions.navigateToSignInPage();
    }

    @When("^User enters valid username and password$")
    public void userEntersValidAnd() throws Exception {
        signInPageActions.loginToCareHires();
    }

    @Given("User logins to carehires")
    public void userLoginsToCarehires() throws Exception {
        signInPageActions.navigateToSignInPage();
        signInPageActions.loginToCareHires();
        overviewActions.waitAndAcceptCookies();
    }
}
