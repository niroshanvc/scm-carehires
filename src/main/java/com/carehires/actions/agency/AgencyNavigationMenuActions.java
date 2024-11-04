package com.carehires.actions.agency;

import com.carehires.pages.agency.AgencyNavigationMenuPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class AgencyNavigationMenuActions {

    private final AgencyNavigationMenuPage navigationMenuPage;

    private static final Logger logger = LogManager.getLogger(AgencyNavigationMenuActions.class);

    public AgencyNavigationMenuActions() {
        navigationMenuPage = new AgencyNavigationMenuPage();
        PageFactory.initElements(BasePage.getDriver(), navigationMenuPage);
    }

    public void gotoCreditServicePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Credit Service page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.creditService);
        BasePage.clickWithJavaScript(navigationMenuPage.creditServiceImage);
    }

    public void gotoLocationsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Locations page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.locations);
    }

    public void gotoStaffPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Staff page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.staff);
    }

    public void gotoBillingPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Billing page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.billing);
    }

    public void gotoUsersPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Users page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.users);
    }

    public void gotoAgreementPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Agreement page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.agreement);
    }

    public void gotoWorkersPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Workers page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.workers);
    }

    public void gotoSettingsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Settings page >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(navigationMenuPage.settings);
    }

}
