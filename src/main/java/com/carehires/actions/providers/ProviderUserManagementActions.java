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
import static org.hamcrest.core.Is.is;

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

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(userManagement.addNewButton);

        enterUserManagementData(YML_FILE, ADD);

        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(userManagement.allAvailableOptions.get(0), 20);
        BasePage.clickWithJavaScript(userManagement.allAvailableOptions.get(1));
        // close the assign to site dropdown
        BasePage.clickWithJavaScript(userManagement.phone);

        String[] userAccessLevel  = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, USER_ACCESS_LEVEL).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
        }

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
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "Name");
        assertThat("User is not added", actual, is(expected));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record created successfully.";
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

        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(userManagement.allAvailableOptions.get(0), 20);
        BasePage.clickWithJavaScript(userManagement.allAvailableOptions.get(1));
        // close the assign to site dropdown
        BasePage.clickWithJavaScript(userManagement.phone);

        String[] userAccessLevel  = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, ADD, USER_ACCESS_LEVEL)).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
        }

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        BasePage.clickWithJavaScript(userManagement.updateButton);

        // edit already added user data
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating User Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(userManagement.editDetailsIcon, 60);
        BasePage.clickWithJavaScript(userManagement.editDetailsIcon);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(userManagement.editDetailsIcon);
        enterUserManagementData(EDIT_YML_FILE, UPDATE);
        updateUserAccessLevel();
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(userManagement.updateButton);
        verifyUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(userManagement.nextButton, 20);
        BasePage.clickWithJavaScript(userManagement.nextButton);
    }

    private void updateUserAccessLevel() {
        // Read access levels from the YAML file and convert them to a Set for easy comparison
        Set<String> desiredLevels = new HashSet<>(Arrays.asList(
                DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, UPDATE, USER_ACCESS_LEVEL).split(",")
        ));
        // Click to open the worker skills dropdown
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        // Get all currently selected skills, default to an empty list if null
        List<String> selectedLevels = getCurrentlySelectedOptions();
        if (selectedLevels == null) {
            selectedLevels = new ArrayList<>();
        }
        // Deselect skills that are not in the desired list
        for (String level : selectedLevels) {
            if (!desiredLevels.contains(level)) {
                BasePage.clickWithJavaScript(getDropdownOptionXpath(level));
            }
        }
        // Select skills that are in the desired list but not currently selected
        for (String level : desiredLevels) {
            if (!selectedLevels.contains(level)) {
                BasePage.clickWithJavaScript(getDropdownOptionXpath(level));
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
        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "email");

        //wait until email get validated
        genericUtils.waitUntilEmailAddressValidatedForUniqueness(email, userManagement.emailAddress, userManagement.name);

        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "Name");
        BasePage.clearAndEnterTexts(userManagement.name, name);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "JobTitle");
        BasePage.clearAndEnterTexts(userManagement.jobTitle,  jobTitle);

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "Phone");
        BasePage.clickWithJavaScript(userManagement.phone);
        BasePage.clearAndEnterTexts(userManagement.phone,  phone);

        String authoriser = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "MarkAsAnAuthoriser");
        assert authoriser != null;
        if(authoriser.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(userManagement.markAsAnAuthoriserToggle);
        }

        //closing the user access level multi select dropdown
        BasePage.clickWithJavaScript(userManagement.phone);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 30);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker staff update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }
}
