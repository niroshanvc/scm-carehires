package com.carehires.steps;

import com.carehires.actions.SignInPageActions;
import com.carehires.actions.providers.ProviderWorkerStaffActions;
import com.carehires.utils.BasePage;
import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) throws Exception {
        SignInPageActions signIn = new SignInPageActions();
        signIn.navigateToSignInPage();
        signIn.loginToCareHires();
        WebDriver driver = BasePage.getDriver();
        driver.get("https://id.dev.v4.carehires.co.uk/login");
        BasePage.waitUntilPageCompletelyLoaded();

        ProviderWorkerStaffActions worker = new ProviderWorkerStaffActions();
        worker.verifyMonthlyAgencySpendValue();
    }
}
