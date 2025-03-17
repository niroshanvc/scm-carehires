package com.carehires.actions.landing;


import com.carehires.pages.landing.DashboardPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

public class DashboardActions {

    DashboardPage dashboardPage;

    public DashboardActions() {
        dashboardPage = new DashboardPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), dashboardPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitUntilSecurityLinkIsAvailable() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(dashboardPage.securityLink, 90);
    }
}