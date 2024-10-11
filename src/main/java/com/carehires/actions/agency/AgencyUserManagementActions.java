package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.AgencyUserManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgencyUserManagementActions {

    AgencyUserManagementPage userManagement;

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "User";
    private static final Logger logger = LogManager.getFormatterLogger(AgencyUserManagementActions.class);

    public AgencyUserManagementActions() {
        userManagement = new AgencyUserManagementPage();
        PageFactory.initElements(BasePage.getDriver(), userManagement);
    }

    public void addUser() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering user details >>>>>>>>>>>>>>>>>>>>");

        // Use the increment value retrieved in the Hooks
        int incrementValue = GlobalVariables.getVariable("incrementValue", Integer.class);

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(userManagement.addNewButton);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "email");
        BasePage.typeWithStringBuilder(userManagement.emailAddress,  (email + incrementValue));
        BasePage.clickWithJavaScript(userManagement.validateEmail);

        //wait until email validated
        BasePage.waitUntilElementPresent(userManagement.name, 30);

        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Name");
        BasePage.typeWithStringBuilder(userManagement.name,  (name + incrementValue));

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "JobTitle");
        BasePage.typeWithStringBuilder(userManagement.jobTitle,  jobTitle);

        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Location");
        BasePage.clickWithJavaScript(userManagement.location);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(location));

        String city = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "City");
        BasePage.clickWithJavaScript(userManagement.city);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(city));

        String[] userAccessLevel  = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "UserAccessLevel").split(",");
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

        BasePage.genericWait(5000);
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
}
