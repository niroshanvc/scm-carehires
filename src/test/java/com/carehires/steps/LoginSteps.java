package com.carehires.steps;

import com.carehires.actions.OverviewActions;
import com.carehires.actions.SignInPageActions;
import com.carehires.actions.landing.DashboardActions;
import com.carehires.utils.BasePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginSteps {

    SignInPageActions signInPageActions = new SignInPageActions();
    OverviewActions overviewActions = new OverviewActions();
    DashboardActions dashboardActions = new DashboardActions();

    @Given("^User navigates to the signinButton page$")
    public void navigateToSingInPage() {
        signInPageActions.navigateToSignInPage();
    }

    @When("^User enters valid username and password$")
    public void entersValidAnd() throws Exception {
        signInPageActions.loginToCareHires();
    }

    @Given("User logins to carehires")
    public void loginToCarehires() throws Exception {
        signInPageActions.navigateToSignInPage();
        signInPageActions.loginToCareHires();
        overviewActions.waitAndAcceptCookies();
    }

    @And("User navigates to SCM page")
    public void navigateToScmage() {
        dashboardActions.waitUntilSecurityLinkIsAvailable();
        BasePage.navigate("scm");
        overviewActions.waitAndAcceptCookies();
    }

    @And("User logins off from Carehires")
    public void loginsOffFromTheSystem() {
        signInPageActions.doLoginOff();
    }

    @And("User again logins to carehires")
    public void loginAgainToCarehires() throws Exception {
        signInPageActions.navigateToSignInPage();
        signInPageActions.loginToCareHires();
    }

    @Given("User logins to carehires as a provider user")
    public void loginAsAProviderUser() throws Exception {
        signInPageActions.navigateToSignInPage();
        signInPageActions.loginAsProviderUser();
        overviewActions.waitAndAcceptCookies();
    }
}
