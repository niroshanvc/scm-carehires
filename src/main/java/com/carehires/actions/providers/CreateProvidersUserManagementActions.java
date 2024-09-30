package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateProvidersUserManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateProvidersUserManagementActions {

    CreateProvidersUserManagementPage userManagement;

    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "User";
    private static final String YML_HEADER_SITE_MANAGEMENT = "SiteManagement";

    public CreateProvidersUserManagementActions() {
        userManagement = new CreateProvidersUserManagementPage();
        PageFactory.initElements(BasePage.getDriver(), userManagement);
    }

    public void addUser() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(userManagement.addNewButton);

        String email = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "email");
        BasePage.typeWithStringBuilder(userManagement.emailAddress,  email);
        BasePage.clickWithJavaScript(userManagement.validateEmail);

        //wait until email validated
        BasePage.genericWait(10000);

        String name = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "Name");
        BasePage.typeWithStringBuilder(userManagement.name,  name);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "JobTitle");
        BasePage.typeWithStringBuilder(userManagement.jobTitle,  jobTitle);

        String site = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER_SITE_MANAGEMENT, "SiteName");
        BasePage.clickWithJavaScript(userManagement.assignToSiteDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(site), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(site));

        String phone = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "Phone");
        BasePage.clickWithJavaScript(userManagement.phone);
        BasePage.typeWithStringBuilder(userManagement.phone,  phone);

        String authoriser = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "MarkAsAnAuthoriser");
        if(authoriser.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(userManagement.markAsAnAuthoriserToggle);
        }

        String[] userAccessLevel  = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "UserAccessLevel").split(",");
        BasePage.clickWithJavaScript(userManagement.userAccessLevel);
        BasePage.genericWait(1000);
        for (String accessLevel : userAccessLevel) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(accessLevel));
        }

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
        String expected = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "Name");
        assertThat("User is not added", actual, is(expected));
    }
}
