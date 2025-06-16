package com.carehires.actions.providers;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProvidersUserManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProviderUserManagementActions {

    ProvidersUserManagementPage userManagement;
    GenericUtils genericUtils;

    {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_FILE_PROVIDER = "provider-user-update-organization";
    private static final String YML_HEADER = "User";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String USER_ACCESS_LEVEL = "UserAccessLevel";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderUserManagementActions.class);
    Integer incrementValue;

    public ProviderUserManagementActions() {
        userManagement = new ProvidersUserManagementPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), userManagement);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering User Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Log the retrieved value
        logger.info("Retrieved provider increment value in UserManagement: %s", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(userManagement.addNewButton);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "email");
        BasePage.genericWait(2000);

        //wait until email get validated
        genericUtils.waitUntilEmailAddressValidatedForUniqueness(email, userManagement.emailAddress,
                userManagement.name);
        enterUserManagementData(YML_FILE, ADD);

        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(userManagement.allAvailableOptions.get(0), 20);
        // select all sites
        BasePage.clickWithJavaScript(userManagement.allAvailableOptions.get(0));
        // close the assign to site dropdown
        BasePage.clickWithJavaScript(userManagement.phone);

        String authoriser = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD,
                "MarkAsAnAuthoriser");
        assert authoriser != null;
        BasePage.scrollToWebElement(userManagement.addButton);
        if(authoriser.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(userManagement.markAsAnAuthoriserToggle);
        }

        String[] userAccessLevel  = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY,
                YML_FILE, YML_HEADER, ADD, USER_ACCESS_LEVEL)).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(userManagement.getDropdownOptionXpath(accessLevel));
        }

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        verifySuccessMessage();
        isUserAdded();

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.updateButton);
        BasePage.genericWait(5000);
        BasePage.waitUntilElementDisplayed(userManagement.nextButton, 60);
        BasePage.clickWithJavaScript(userManagement.nextButton);
    }

    private void isUserAdded() {
        BasePage.waitUntilElementPresent(userManagement.fullNameCell, 60);
        String nameWithJob = BasePage.getText(userManagement.fullNameCell);
        String actual = nameWithJob.split("\n")[0].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD,
                "Name");
        assertThat("User is not added", actual, is(expected));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record created successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("User management information success message is wrong!", actualInLowerCase, is(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }

    public void updateUserData() {
        navigationMenu.gotoUsersPage();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering User Management - In Edit >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // add new user
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(userManagement.addNewButton);
        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, ADD,
                "email");
        BasePage.genericWait(2000);

        //wait until email get validated
        genericUtils.waitUntilEmailAddressValidatedForUniqueness(email, userManagement.emailAddress, userManagement.
                name);
        enterUserManagementData(EDIT_YML_FILE, ADD);

        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(userManagement.allAvailableOptions.get(0), 20);
        // select all sites
        BasePage.clickWithJavaScript(userManagement.allAvailableOptions.get(0));
        // close the assign to site dropdown
        BasePage.clickWithJavaScript(userManagement.phone);

        String authoriser = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                ADD, "MarkAsAnAuthoriser");
        assert authoriser != null;
        BasePage.scrollToWebElement(userManagement.addButton);
        if(authoriser.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(userManagement.markAsAnAuthoriserToggle);
        }

        String[] userAccessLevel  = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY,
                EDIT_YML_FILE, YML_HEADER, ADD, USER_ACCESS_LEVEL)).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(userManagement.getDropdownOptionXpath(accessLevel));
        }

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        BasePage.clickWithJavaScript(userManagement.updateButton);

        // edit already added user data
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating User Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverAndClick(userManagement.actionsThreeDots, userManagement.editUser,
                ProvidersUserManagementPage.editUserChildElement);
        BasePage.genericWait(3000);
        enterUserManagementData(EDIT_YML_FILE, UPDATE);
        updateUserAccessLevel();
        BasePage.genericWait(15000);
        BasePage.clickWithJavaScript(userManagement.updateButton);
        verifyUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(userManagement.nextButton, 20);
        BasePage.clickWithJavaScript(userManagement.nextButton);
    }

    private void updateUserAccessLevel() {
        // Read access levels from the YAML file and convert them to a Set for easy comparison
        Set<String> desiredLevels = new HashSet<>(Arrays.asList(
                Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                        UPDATE, USER_ACCESS_LEVEL)).split(",")
        ));

        // Click to open the worker skills dropdown
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);

        // Get all currently selected skills
        List<String> selectedLevels = getCurrentlySelectedOptions();

        // If getCurrentlySelectedOptions() always returns a non-null list (even if empty),
        // this check is redundant

        // Deselect skills that are not in the desired list
        for (String level : selectedLevels) {
            if (!desiredLevels.contains(level)) {
                BasePage.clickWithJavaScript(userManagement.getDropdownOptionXpath(level));
            }
        }

        // Select skills that are in the desired list but not currently selected
        for (String level : desiredLevels) {
            if (!selectedLevels.contains(level)) {
                BasePage.clickWithJavaScript(userManagement.getDropdownOptionXpath(level));
            }
        }
    }

    private List<String> getCurrentlySelectedOptions() {
        // Retrieve elements representing selected levels
        List<WebElement> selectedLevelsElements = userManagement.alreadySelectedOptions;

        List<String> selectedLevels = new ArrayList<>();
        for (WebElement element : selectedLevelsElements) {
            selectedLevels.add(element.getText());
        }
        return selectedLevels;
    }

    private void enterUserManagementData(String ymlFile, String subHeader) {
        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "Name");
        BasePage.waitUntilElementDisplayed(userManagement.name, 30);
        BasePage.clearAndEnterTexts(userManagement.name, name);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "JobTitle");
        BasePage.clearAndEnterTexts(userManagement.jobTitle,  jobTitle);

        String phone = DataConfigurationReader.generateUkMobileNumber();
        BasePage.clickWithJavaScript(userManagement.phone);
        BasePage.clearAndEnterTexts(userManagement.phone,  phone);

        //closing the user access level multi select dropdown
        BasePage.clickWithJavaScript(userManagement.phone);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker user update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }

    public void updateUserManagementData() {
        BasePage.waitUntilPageCompletelyLoaded();
        navigationMenu.gotoUsersPage();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Entering User Management - In Edit >>>>>>>>>>>>>>>>>>>>>>");

        // add new user
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(userManagement.addNewButton);
        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, ADD,
                "email");
        BasePage.genericWait(2000);

        //wait until email get validated
        genericUtils.waitUntilEmailAddressValidatedForUniqueness(email, userManagement.emailAddress, userManagement.
                name);
        enterUserManagementData(YML_FILE_PROVIDER, ADD);

        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(userManagement.allAvailableOptions.get(0), 20);
        // select all sites
        BasePage.clickWithJavaScript(userManagement.allAvailableOptions.get(0));
        // close the assign to site dropdown
        BasePage.clickWithJavaScript(userManagement.phone);

        String authoriser = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_PROVIDER,
                YML_HEADER, ADD, "MarkAsAnAuthoriser");
        assert authoriser != null;
        BasePage.scrollToWebElement(userManagement.addButton);
        if(authoriser.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(userManagement.markAsAnAuthoriserToggle);
        }

        String[] userAccessLevel  = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY,
                YML_FILE_PROVIDER, YML_HEADER, ADD, USER_ACCESS_LEVEL)).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(userManagement.getDropdownOptionXpath(accessLevel));
        }

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        BasePage.clickWithJavaScript(userManagement.updateButton);

        // edit already added user data
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Updating User Management Information - In Edit >>>>>>>>>>>>>>>>>>>>>>");
        moveToLastThreeDots();
        BasePage.clickWithJavaScript(userManagement.editUser);
        BasePage.genericWait(3000);
        enterUserManagementData(YML_FILE_PROVIDER, UPDATE);
        updateUserAccessLevel();
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.updateButton);
        verifyUpdateSuccessMessage();
    }

    private void moveToLastThreeDots() {
        List<WebElement> list = userManagement.threeDots;
        BasePage.mouseHoverOverElement(list.get(list.size() - 1));
    }
}
