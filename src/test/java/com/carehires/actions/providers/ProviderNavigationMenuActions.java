package com.carehires.actions.providers;


import com.carehires.pages.providers.ProviderNavigationMenuPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class ProviderNavigationMenuActions {

    private final ProviderNavigationMenuPage navigationMenuPage;

    private static final Logger logger = LogManager.getLogger(ProviderNavigationMenuActions.class);

    public ProviderNavigationMenuActions() {
        navigationMenuPage = new ProviderNavigationMenuPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), navigationMenuPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void gotoProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Profile page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.profile);
        BasePage.clickWithJavaScript(navigationMenuPage.profileImage);
    }

    public void gotoSitePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Site page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.site);
        BasePage.clickWithJavaScript(navigationMenuPage.siteImage);
    }

    public void gotoStaffPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Staff page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.staff);
        BasePage.clickWithJavaScript(navigationMenuPage.staffImage);
    }

    public void gotoUsersPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Users page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.users);
        BasePage.clickWithJavaScript(navigationMenuPage.usersImage);
    }

    public void gotoBillingPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Billing page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.billingDiv);
        BasePage.clickWithJavaScript(navigationMenuPage.billingImage);
    }

    public void gotoBillingPageUsingCircleIcon() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Billing page Using Circle Icon Menu >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.billing);
        BasePage.clickWithJavaScript(navigationMenuPage.billingIcon);
    }

    public void gotoAgreementPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Agreement page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.agreement);
        BasePage.clickWithJavaScript(navigationMenuPage.agreementImage);
    }

    public void gotoSettingsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Settings page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.settings);
        BasePage.clickWithJavaScript(navigationMenuPage.settingsImage);
    }

    public void gotoRestrictionsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Restrictions page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.restrictions);
        BasePage.clickWithJavaScript(navigationMenuPage.restrictionsImage);
    }

    public void gotoNotificationsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Notifications page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.notifications);
        BasePage.clickWithJavaScript(navigationMenuPage.notificationsImage);
    }
}
