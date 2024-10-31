package com.carehires.actions.landing;

import com.carehires.pages.landing.DashboardPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

public class DashboardActions {

    DashboardPage dashboardPage;

    public DashboardActions() {
        dashboardPage = new DashboardPage();
        PageFactory.initElements(BasePage.getDriver(), dashboardPage);
    }

    public void waitUntilSecurityLinkIsAvailable() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(dashboardPage.securityLink, 90);
    }
}