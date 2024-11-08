package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.AgencyUserManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgencyUserManagementActions {

    private final AgencyUserManagementPage userManagement;
    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "User";
    private static final String EDIT_YML_HEADER = "Add";
    private static final String EDIT_YML_HEADER2 = "Edit";
    private static final String USER_ACCESS_LEVEL = "UserAccessLevel";

    private static final Logger logger = LogManager.getFormatterLogger(AgencyUserManagementActions.class);

    public AgencyUserManagementActions() {
        userManagement = new AgencyUserManagementPage();
        PageFactory.initElements(BasePage.getDriver(), userManagement);
    }

    public void addUser() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering user details >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(userManagement.addNewButton);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "email");
        BasePage.typeWithStringBuilder(userManagement.emailAddress, email);
        BasePage.clickWithJavaScript(userManagement.validateEmail);

        //wait until email validated
        BasePage.waitUntilElementPresent(userManagement.name, 30);

        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Name");
        BasePage.typeWithStringBuilder(userManagement.name, name);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "JobTitle");
        BasePage.typeWithStringBuilder(userManagement.jobTitle,  jobTitle);

        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Location");
        BasePage.clickWithJavaScript(userManagement.location);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(location));

        String city = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "City");
        BasePage.clickWithJavaScript(userManagement.city);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(city));

        String[] userAccessLevel  = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, USER_ACCESS_LEVEL).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
        }

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Phone");
        BasePage.clickWithJavaScript(userManagement.phone);
        BasePage.typeWithStringBuilder(userManagement.phone,  phone);

        BasePage.genericWait(6000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        verifySuccessMessage();
        isUserAdded();

        BasePage.clickWithJavaScript(userManagement.updateButton);
        BasePage.waitUntilElementClickable(userManagement.nextButton, 60);
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
        BasePage.waitUntilElementPresent(userManagement.successMessage, 90);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("User information saved success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }

    public void editUserManagement() {
        navigateToUserManagementPage();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating user details - Add new User >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(userManagement.addNewButton);
        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, EDIT_YML_HEADER, "email");
        BasePage.clearAndEnterTexts(userManagement.emailAddress, email);
        BasePage.clickWithJavaScript(userManagement.validateEmail);
        enterData(EDIT_YML_HEADER);
        // select access levels
        String[] userAccessLevel  = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, EDIT_YML_HEADER, USER_ACCESS_LEVEL).split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
        }

        BasePage.genericWait(6000);
        BasePage.clickWithJavaScript(userManagement.addButton);
        verifySuccessMessage();

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating user details - Edit a User >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementDisplayed(userManagement.editDetailsIcon, 30);
        BasePage.clickWithJavaScript(userManagement.editDetailsIcon);
        enterData(EDIT_YML_HEADER2);
        // select access levels in edit mode
        updateUserAccessLevel();
        BasePage.genericWait(6000);

        BasePage.clickWithJavaScript(userManagement.updateButton);
        verifyUserInfoUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(userManagement.nextButton, 60);
        BasePage.clickWithJavaScript(userManagement.nextButton);
    }

    private void enterData(String header) {
        //wait until email validated
        BasePage.waitUntilElementPresent(userManagement.name, 30);

        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, header, "Name");
        BasePage.clearAndEnterTexts(userManagement.name, name);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, header, "JobTitle");
        BasePage.clearAndEnterTexts(userManagement.jobTitle,  jobTitle);

        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, header, "Location");
        BasePage.clickWithJavaScript(userManagement.location);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(location));

        String city = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, header, "City");
        BasePage.clickWithJavaScript(userManagement.city);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(city));

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, header, "Phone");
        BasePage.clickWithJavaScript(userManagement.phone);
        BasePage.clearAndEnterTexts(userManagement.phone,  phone);
    }

    private void navigateToUserManagementPage() {
        navigationMenu.gotoUsersPage();
        BasePage.genericWait(3000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Agency User Management >>>>>>>>>>>>>>>>>>>>");
        if (BasePage.isElementDisplayed(userManagement.updateButton)) {
            BasePage.clickWithJavaScript(userManagement.updateButton);
        }
        BasePage.waitUntilElementPresent(userManagement.nextButton, 30);
        BasePage.genericWait(2000);
    }

    private void verifyUserInfoUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(userManagement.successMessage, 90);
        String actualInLowerCase = BasePage.getText(userManagement.successMessage).toLowerCase().trim();
        String expected = "Record update successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("User info update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(userManagement.successMessage, 20);
    }

    private void updateUserAccessLevel() {
        // Read access levels from the YAML file and convert them to a Set for easy comparison
        Set<String> desiredAccessLevels = new HashSet<>(Arrays.asList(
                DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, EDIT_YML_HEADER2, USER_ACCESS_LEVEL).split(",")
        ));
        // Click to open the access levels dropdown
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        // Get all currently selected access level, default to an empty list if null
        List<String> selectedAccessLevels = getCurrentlySelectedAccessLevels();
        if (selectedAccessLevels == null) {
            selectedAccessLevels = new ArrayList<>();
        }
        // Deselect access levels that are not in the desired list
        for (String accessLevel : selectedAccessLevels) {
            if (!desiredAccessLevels.contains(accessLevel)) {
                BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
            }
        }
        // Select access level that are in the desired list but not currently selected
        for (String accessLevel : desiredAccessLevels) {
            if (!selectedAccessLevels.contains(accessLevel)) {
                BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
            }
        }
    }

    private List<String> getCurrentlySelectedAccessLevels() {
        // Retrieve elements representing selected access levels
        List<WebElement> selectedAccessLevelElements = userManagement.alreadySelectedAccessLevels;

        List<String> selectedAccessLevels = new ArrayList<>();
        for (WebElement element : selectedAccessLevelElements) {
            selectedAccessLevels.add(element.getText()); // Assuming the text of each element represents the access level name
        }
        return selectedAccessLevels;
    }
}
