package com.carehires.actions.providers;

import com.carehires.pages.providers.ProviderNotificationManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProviderNotificationManagementActions {

    ProviderNotificationManagementPage notificationManagement;
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-user-update-organization";
    private static final String YML_HEADER = "Notification Management";

    private static final Logger logger = LogManager.getFormatterLogger(ProviderNotificationManagementActions.class);

    public ProviderNotificationManagementActions() {
        notificationManagement = new ProviderNotificationManagementPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), notificationManagement);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNotificationData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Update Notification Data >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(2000);
        navigationMenu.gotoNotificationsPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(1000);
        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, "Worker Staff Management",
                "Site");
        BasePage.waitUntilElementClickable(notificationManagement.siteDropdown, 20);
        BasePage.clickWithJavaScript(notificationManagement.siteDropdown);
        assert site != null;
        if (site.contains("AAAA")) {
            BasePage.clickWithJavaScript(notificationManagement.firstOptionInSiteDropdown);
        } else {
            BasePage.clickWithJavaScript(ProviderNotificationManagementPage.getDropdownXpath(site));
        }

        BasePage.genericWait(500);

        String job = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                "Job Notification Address");
        BasePage.clearAndEnterTexts(notificationManagement.jobNotificationAddress, job);

        String approval = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                "Job Notification Address");
        BasePage.clearAndEnterTexts(notificationManagement.approvalNotificationAddress, approval);
        BasePage.clickWithJavaScript(notificationManagement.saveButton);
        verifyDataSavedSuccessMessage();
    }

    private void verifyDataSavedSuccessMessage() {
        BasePage.waitUntilElementPresent(notificationManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(notificationManagement.successMessage).toLowerCase().trim();
        String expected = "Config saved successfully!";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Configure notification data is not working!", actualInLowerCase, is(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(notificationManagement.successMessage, 20);
    }
}
