package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProvidersUserManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProviderUserManagementActions {

    ProvidersUserManagementPage userManagement;
    GenericUtils genericUtils = new GenericUtils();
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_HEADER = "User";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_HEADER_SITE_MANAGEMENT = "Site Management";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderUserManagementActions.class);
    Integer incrementValue;

    public ProviderUserManagementActions() {
        userManagement = new ProvidersUserManagementPage();
        PageFactory.initElements(BasePage.getDriver(), userManagement);
    }

    public void addUser() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering User Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();

        enterUserManagementData(YML_FILE, ADD);

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        verifySuccessMessage();
        isUserAdded();

        BasePage.clickWithJavaScript(userManagement.updateButton);
        BasePage.waitUntilElementDisplayed(userManagement.nextButton, 60);
        BasePage.waitUntilElementClickable(userManagement.nextButton, 20);
        BasePage.clickWithJavaScript(userManagement.nextButton);
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private void isUserAdded() {
        BasePage.waitUntilElementPresent(userManagement.fullNameCell, 60);
        String nameWithJob = BasePage.getText(userManagement.fullNameCell);
        String actual = nameWithJob.split("\n")[0].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Name");
        assertThat("User is not added", actual, is(expected));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("User management information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }

    public void updateUserData() {
        navigationMenu.gotoUsersPage();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering User Management - In Edit >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // add new user
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(userManagement.addNewButton);
        enterUserManagementData(EDIT_YML_FILE, ADD);
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        BasePage.clickWithJavaScript(userManagement.updateButton);

        // edit already added user data
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating User Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(userManagement.editDetailsIcon, 60);
        BasePage.clickWithJavaScript(userManagement.editDetailsIcon);
        enterUserManagementData(EDIT_YML_FILE, UPDATE);
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.updateButton);
        verifyUpdateSuccessMessage();
    }

    private void enterUserManagementData(String ymlFile, String subHeader) {
        BasePage.clickWithJavaScript(userManagement.addNewButton);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "email");

        //wait until email get validated
        genericUtils.waitUntilEmailAddressValidatedForUniqueness(email, userManagement.emailAddress, userManagement.name);

        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "Name");
        BasePage.typeWithStringBuilder(userManagement.name, name);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "JobTitle");
        BasePage.typeWithStringBuilder(userManagement.jobTitle,  jobTitle);

        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER_SITE_MANAGEMENT, subHeader, "SiteName");
        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(site), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(site));

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "Phone");
        BasePage.clickWithJavaScript(userManagement.phone);
        BasePage.typeWithStringBuilder(userManagement.phone,  phone);

        String authoriser = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "MarkAsAnAuthoriser");
        if(authoriser.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(userManagement.markAsAnAuthoriserToggle);
        }

        String[] userAccessLevel  = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "UserAccessLevel").split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
        }

        //closing the user access level multi select dropdown
        BasePage.clickWithJavaScript(userManagement.phone);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record update successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker staff update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }
}
