package com.carehires.actions.providers;

import com.carehires.pages.providers.ProviderSettingsPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProviderSettingsActions {

    private final ProviderSettingsPage settingsPage;

    private static final Logger logger = LogManager.getLogger(ProviderSettingsActions.class);

    public ProviderSettingsActions() {
        settingsPage = new ProviderSettingsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), settingsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void enableVisibilityAndDisableMandatory(String siteName) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Enabling Visibility >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(settingsPage.imgSettings, 60);
        BasePage.clickWithJavaScript(settingsPage.imgSettings);
        BasePage.waitUntilPageCompletelyLoaded();
        doOpenSiteNamePopup();
        doEnableVisibleCheckbox(siteName);
        doDisableMandatoryCheckbox(siteName);
        BasePage.clickWithJavaScript(settingsPage.siteNamePopupSaveButton);
        verifySuccessMessage();
    }

    private void doEnableVisibleCheckbox(String siteName) {
        String attr = BasePage.getAttributeValue(settingsPage.getSiteNameVisibleCheckboxSpan(siteName),
                "class");
        if (!attr.contains("checked")) {
            BasePage.clickWithJavaScript(settingsPage.getSiteNameVisibleCheckboxXpath(siteName));
        }
    }

    private void doOpenSiteNamePopup() {
        BasePage.waitUntilElementPresent(settingsPage.updateButton, 60);
        BasePage.clickWithJavaScript(settingsPage.updateButton);
        BasePage.waitUntilElementClickable(settingsPage.editSettingsButton, 30);
        BasePage.clickWithJavaScript(settingsPage.editSettingsButton);
        BasePage.genericWait(3000);
    }

    private void doDisableMandatoryCheckbox(String siteName) {
        String attr = BasePage.getAttributeValue(settingsPage.getSiteNameMandatoryCheckboxSpan(siteName),
                "class");
        if (attr.contains("checked")) {
            BasePage.clickWithJavaScript(settingsPage.getSiteNameMandatoryCheckboxXpath(siteName));
        }
    }

    private void doEnableMandatoryCheckbox(String siteName) {
        String attr = BasePage.getAttributeValue(settingsPage.getSiteNameMandatoryCheckboxSpan(siteName),
                "class");
        if (!attr.contains("checked")) {
            BasePage.clickWithJavaScript(settingsPage.getSiteNameMandatoryCheckboxXpath(siteName));
        }
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(settingsPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(settingsPage.successMessage).toLowerCase().trim();
        String expected = "Successfully updated pre approval reasons for sites.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Pr-approval was not saved!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(settingsPage.successMessage, 20);
    }

    public void enableVisibilityAndMandatory(String siteName) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Enabling Visibility and Mandatory >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(settingsPage.imgSettings, 60);
        BasePage.clickWithJavaScript(settingsPage.imgSettings);
        BasePage.waitUntilPageCompletelyLoaded();
        doOpenSiteNamePopup();
        doEnableVisibleCheckbox(siteName);
        doEnableMandatoryCheckbox(siteName);
        BasePage.clickWithJavaScript(settingsPage.siteNamePopupSaveButton);
        verifySuccessMessage();
    }
}
