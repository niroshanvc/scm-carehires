package com.carehires.steps;

import com.carehires.actions.SignInPageActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginSteps {

    SignInPageActions signInPageActions = new SignInPageActions();

    @Given("^User navigates to the signin page$")
    public void navigateToSinginPage() {
        signInPageActions.navigateToSigninPage();
    }

    @When("^User enters valid username and password$")
    public void userEntersValidAnd() {
        signInPageActions.loginToCareHires();
    }

    @Given("User logins to carehires")
    public void userLoginsToCarehires() {
        signInPageActions.navigateToSigninPage();
        signInPageActions.loginToCareHires();
    }
}
