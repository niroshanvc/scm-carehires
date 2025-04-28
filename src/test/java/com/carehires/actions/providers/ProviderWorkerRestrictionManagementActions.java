package com.carehires.actions.providers;

import com.carehires.pages.providers.ProviderWorkerRestrictionManagementPage;
import com.carehires.pages.providers.WorkerStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProviderWorkerRestrictionManagementActions {

    ProviderWorkerRestrictionManagementPage workerRestriction;
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-user-update-organization";
    private static final String YML_HEADER = "User";

    private static final Logger logger = LogManager.getFormatterLogger(ProviderWorkerRestrictionManagementActions.class);

    public ProviderWorkerRestrictionManagementActions() {
        workerRestriction = new ProviderWorkerRestrictionManagementPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), workerRestriction);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRestriction() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Restrict a worker >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        navigationMenu.gotoRestrictionsPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(workerRestriction.addNewButton);

        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, "Worker Staff Management",
                "Site");
        BasePage.waitUntilElementClickable(workerRestriction.siteDropdown, 20);
        BasePage.clickWithJavaScript(workerRestriction.siteDropdown);
        assert site != null;
        if (site.contains("AAAA")) {
            BasePage.clickWithJavaScript(workerRestriction.firstOptionInSiteDropdown);
        } else {
            BasePage.clickWithJavaScript(WorkerStaffPage.getDropdownXpath(site));
        }

        BasePage.genericWait(500);
        BasePage.clickWithJavaScript(workerRestriction.agencyDropdown);
        BasePage.clickWithJavaScript(workerRestriction.firstOptionInAgencyDropdown);
        BasePage.clearAndEnterTexts(workerRestriction.workerInputField, "aut");
        BasePage.waitUntilElementClickable(workerRestriction.firstWorker, 60);
        BasePage.clickWithJavaScript(workerRestriction.firstWorker);
        String reason = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, "Restrictions", "Reason");
        BasePage.clearAndEnterTexts(workerRestriction.reasonTextArea, reason);
        BasePage.clickWithJavaScript(workerRestriction.addButton);
        verifyWorkerRestrictionSuccessMessage();
    }

    private void verifyWorkerRestrictionSuccessMessage() {
        BasePage.waitUntilElementPresent(workerRestriction.successMessage, 30);
        String actualInLowerCase = BasePage.getText(workerRestriction.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker restriction is not working!", actualInLowerCase, is(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(workerRestriction.successMessage, 20);
    }

    public void acceptWorker() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Accept a Restricted worker >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(1000);
        navigationMenu.gotoRestrictionsPage();
        BasePage.waitUntilPageCompletelyLoaded();
       BasePage.waitUntilElementDisplayed(workerRestriction.pendingApprovalTab, 60);
        BasePage.clickWithJavaScript(workerRestriction.pendingApprovalTab);
        BasePage.waitUntilElementClickable(workerRestriction.firstApproveButton, 20);
        BasePage.clickWithJavaScript(workerRestriction.firstApproveButton);
    }

    public void verifyRestrictedWorkerDisplayInTableGrid() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Restricted worker >>>>>>>>>>>>>>>>>>>>");
        BasePage.genericWait(3000);
        navigationMenu.gotoRestrictionsPage();
        BasePage.waitUntilPageCompletelyLoaded();
        WebElement ele =  BasePage.getElement(workerRestriction.firstRowInTable);
        assertThat("Restricted worker is not displayed in the table grid", ele.isDisplayed(), is(true));
    }

    public void removeRestrictedWorker() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Remove Restricted worker >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(workerRestriction.updateButton);
        BasePage.waitUntilElementClickable(workerRestriction.firstDeleteIcon, 20);
        BasePage.clickWithJavaScript(workerRestriction.firstDeleteIcon);
        BasePage.waitUntilElementClickable(workerRestriction.confirmButton, 20);
        BasePage.clickWithJavaScript(workerRestriction.confirmButton);
        verifyRestrictedWorkerRemoveSuccessMessage();
    }

    private void verifyRestrictedWorkerRemoveSuccessMessage() {
        BasePage.waitUntilElementPresent(workerRestriction.successMessage, 30);
        String actualInLowerCase = BasePage.getText(workerRestriction.successMessage).toLowerCase().trim();
        String expected = "Record deleted successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Restricted worker remove is not working!", actualInLowerCase, is(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(workerRestriction.successMessage, 20);
    }
}
