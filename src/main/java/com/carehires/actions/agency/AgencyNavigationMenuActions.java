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
        BasePage.mouseHoverOverElement(navigationMenuPage.locations);
        BasePage.clickWithJavaScript(navigationMenuPage.locationsImage);
    }

    public void gotoStaffPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Staff page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.staff);
        BasePage.clickWithJavaScript(navigationMenuPage.staffImage);
    }

    public void gotoBillingPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Billing page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.billing);
        BasePage.clickWithJavaScript(navigationMenuPage.billingImage);
    }

    public void gotoUsersPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Users page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.users);
        BasePage.clickWithJavaScript(navigationMenuPage.usersImage);
    }

    public void gotoAgreementPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Agreement page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.agreement);
        BasePage.clickWithJavaScript(navigationMenuPage.agreementImage);
    }

    public void gotoWorkersPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Workers page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.workers);
        BasePage.clickWithJavaScript(navigationMenuPage.workersImage);
    }

    public void gotoSettingsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Settings page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.settings);
        BasePage.clickWithJavaScript(navigationMenuPage.settingsImage);
    }

    public void gotoProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Profile page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.profile);
        BasePage.clickWithJavaScript(navigationMenuPage.profileImage);
    }
}
