package com.carehires.actions.agency;

import com.carehires.pages.agency.OrganizationalSettingsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.support.PageFactory;

public class OrganizationalSettingsActions {

    OrganizationalSettingsPage settingsPage;

    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "Settings";

    public OrganizationalSettingsActions() {
        settingsPage = new OrganizationalSettingsPage();
        PageFactory.initElements(BasePage.getDriver(), settingsPage);
    }

    public void saveSettings() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(settingsPage.updateButton);
        BasePage.waitUntilElementPresent(settingsPage.saveButton, 20);

        String timeFormat = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "TimeFormat");
        BasePage.clickWithJavaScript(settingsPage.timeFormatDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(timeFormat), 10);
        if (timeFormat.equals("12 Hours")) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(timeFormat));
        } else {
            BasePage.clickWithJavaScript(getDropdownOptionXpath("24 Hours"));
        }

        String hubspot = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "HubSpotLink");
        BasePage.typeWithStringBuilder(settingsPage.hubSpotLink, hubspot);

        String theme = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "InvoiceBrandingTheme");
        BasePage.clickWithJavaScript(settingsPage.invoiceBrandingThemeDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(theme));

        String salesPerson = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SalesProcessCompletedBy");
        BasePage.clickWithJavaScript(settingsPage.salesProcessCompletedByDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(salesPerson));

        String onboard = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "OnboardedBDM");
        BasePage.clickWithJavaScript(settingsPage.onboardedBdmDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(onboard));

        BasePage.clickWithJavaScript(settingsPage.saveButton);
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }
}
