package com.carehires.steps;

import com.carehires.actions.SignInPageActions;
import com.carehires.actions.providers.CreateWorkerStaffActions;
import com.carehires.utils.BasePage;
import org.openqa.selenium.WebDriver;

public class Mail {
    public static void main(String[] args) {
        SignInPageActions signIn = new SignInPageActions();
        signIn.navigateToSignInPage();
        signIn.loginToCareHires();
        WebDriver driver = BasePage.getDriver();
        driver.get("https://id.dev.v4.carehires.co.uk/login");
        BasePage.waitUntilPageCompletelyLoaded();

        CreateWorkerStaffActions worker = new CreateWorkerStaffActions();
        worker.verifyMonthlyAgencySpendValue();
    }
}
