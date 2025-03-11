package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProvidersSiteManagementPage;
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

public class ProviderSiteManagementActions {

    private final ProvidersSiteManagementPage siteManagementPage;
    private static final GenericUtils genericUtils;

    static {
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
    private static final String YML_HEADER = "Site Management";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_HEADER_DATASET1 = "Dataset1";
    private static final String YML_HEADER_DATASET2 = "Dataset2";
    private static final String SITE_SPECIALISM = "SiteSpecialism";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderSiteManagementActions.class);
    Integer incrementValue;

    public ProviderSiteManagementActions() {
        siteManagementPage = new ProvidersSiteManagementPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), siteManagementPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSiteManagementData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Site Management Information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Log the retrieved value
        logger.info("Retrieved provider increment value in SiteManagement: %s", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        // add first site
        BasePage.clickWithJavaScript(siteManagementPage.addNewButton);
        enterSiteManagementData(YML_FILE, ADD, YML_HEADER_DATASET1);
        logger.info("Entering Site Specialism");
        BasePage.scrollToWebElement(siteManagementPage.siteTypeDropdown);
        String[] siteSpecialism = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, YML_HEADER_DATASET1, SITE_SPECIALISM)).split(",");
        BasePage.clickWithJavaScript(siteManagementPage.siteSpecialismMultiSelectDropdown);
        BasePage.genericWait(500);
        for (String specialism : siteSpecialism) {
            BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(specialism));
        }
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);

        BasePage.scrollToWebElement(siteManagementPage.addButton);
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(siteManagementPage.addButton);
        verifySuccessMessage();
        isSiteSaved(YML_FILE, ADD, YML_HEADER_DATASET1);

        // add second site
        BasePage.clickWithJavaScript(siteManagementPage.addNewButton);
        enterSiteManagementData(YML_FILE, ADD, YML_HEADER_DATASET2);
        logger.info("Entering Site Specialism");
        BasePage.scrollToWebElement(siteManagementPage.siteTypeDropdown);
        String[] siteSpecialism2 = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, YML_HEADER_DATASET2, SITE_SPECIALISM)).split(",");
        BasePage.clickWithJavaScript(siteManagementPage.siteSpecialismMultiSelectDropdown);
        BasePage.genericWait(500);
        for (String specialism : siteSpecialism2) {
            BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(specialism));
        }
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(siteManagementPage.addButton);

        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        BasePage.waitUntilElementClickable(siteManagementPage.nextButton, 90);
        BasePage.clickWithJavaScript(siteManagementPage.nextButton);
    }

    private void enterSiteManagementData(String ymlFile, String subHeader, String dataset) {
        logger.info("Entering Site Name");
        String siteName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "SiteName");
        BasePage.clearAndEnterTexts(siteManagementPage.siteName, siteName);

        logger.info("Entering Site Type");
        String siteType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "SiteType");
        BasePage.clickWithJavaScript(siteManagementPage.siteTypeDropdown);
        BasePage.waitUntilElementClickable(getDropdownXpath(siteType), 30);
        BasePage.clickWithJavaScript(getDropdownXpath(siteType));

        logger.info("Entering Site also known as data");
        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "AlsoKnownAs");
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);
        BasePage.clearAndEnterTexts(siteManagementPage.alsoKnownAs, alsoKnownAs);

        logger.info("Entering Site Email");
        String siteEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "SiteEmail");
        BasePage.clearAndEnterTexts(siteManagementPage.siteEmail, siteEmail);

        logger.info("Entering Site Address");
        String postalCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "PostalCode");
        BasePage.clearTexts(siteManagementPage.postalCode);
        genericUtils.fillAddress(siteManagementPage.postalCode, postalCode, 1000);

        logger.info("Entering Site No of beds");
        String numberOfBeds = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "NoOfBeds");
        BasePage.clearAndEnterTexts(siteManagementPage.noOfBeds, numberOfBeds);

        logger.info("Entering Site Code");
        String siteCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "SiteCode");
        BasePage.clearAndEnterTexts(siteManagementPage.siteCode, siteCode);

        logger.info("Entering Site Phone Number");
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, siteManagementPage.phoneNumber, YML_HEADER, subHeader, dataset, "PhoneNumberType");
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, siteManagementPage.phoneNumber, YML_HEADER, subHeader, dataset, "PhoneNumber");

        logger.info("Entering Site job notification email address");
        BasePage.scrollToWebElement(siteManagementPage.approvalNotificationAddress);
        String jobNotification = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "JobNotificationAddress");
        BasePage.clearAndEnterTexts(siteManagementPage.jobNotificationAddress, jobNotification);

        logger.info("Entering site approval notification email address");
        String approvalNotification = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "ApprovalNotificationAddress");
        BasePage.clearAndEnterTexts(siteManagementPage.approvalNotificationAddress, approvalNotification);
    }

    private void isSiteSaved(String ymlFile, String subHeader, String dataset) {
        BasePage.waitUntilElementPresent(siteManagementPage.siteNameAddress, 90);
        String actualSiteName = BasePage.getText(siteManagementPage.siteNameAddress).trim();
        String expectedSiteName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "SiteName");
        assertThat("Site is not saved", actualSiteName, is(expectedSiteName));
    }

    private String getDropdownXpath(String text) {
        return String.format("//nb-option//div[contains(text(),'%s')]", text);
    }

    private String getMultiSelectDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(siteManagementPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(siteManagementPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Site management information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(siteManagementPage.successMessage, 20);
    }

    public void updateSiteInfo() {
        navigationMenu.gotoSitePage();
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Site Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(siteManagementPage.addNewButton);
        enterSiteManagementData(EDIT_YML_FILE, ADD, YML_HEADER_DATASET1);

        logger.info("Entering Site Specialism - In Add");
        String[] siteSpecialism = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, ADD, YML_HEADER_DATASET1, SITE_SPECIALISM)).split(",");
        BasePage.clickWithJavaScript(siteManagementPage.siteSpecialismMultiSelectDropdown);
        BasePage.genericWait(500);
        for (String specialism : siteSpecialism) {
            BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(specialism));
        }
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);

        BasePage.clickWithJavaScript(siteManagementPage.addButton);
        verifySuccessMessage();
        BasePage.waitUntilElementClickable(siteManagementPage.updateButton, 90);

        // edit already added site data
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Editing Site Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        BasePage.waitUntilElementDisplayed(siteManagementPage.editDetailsIcon, 30);
        BasePage.clickWithJavaScript(siteManagementPage.editDetailsIcon);
        enterSiteManagementData(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET1);

        BasePage.scrollToWebElement(siteManagementPage.siteTypeDropdown);
        updateSiteSpecialism();
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);

        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        verifyUpdateSuccessMessage();

        // click on next button
        BasePage.clickWithJavaScript(siteManagementPage.nextButton);
    }

    private void updateSiteSpecialism() {
        Set<String> siteSpecialism = new HashSet<>(Arrays.asList(Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY,
                EDIT_YML_FILE, YML_HEADER, UPDATE, YML_HEADER_DATASET1, SITE_SPECIALISM)).split(",")));
        BasePage.clickWithJavaScript(siteManagementPage.siteSpecialismMultiSelectDropdown);
        // Get all currently selected skills, default to an empty list if null
        List<String> selectedSiteSpecialism = getCurrentlySelectedSiteSpecialism();
        if (selectedSiteSpecialism == null) {
            selectedSiteSpecialism = new ArrayList<>();
        }
        // Deselect site specialisms that are not in the desired list
        for (String option : selectedSiteSpecialism) {
            if (!siteSpecialism.contains(option)) {
                BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(option));
            }
        }

        // Select site specialism that are in the desired list but not currently selected
        for (String option : siteSpecialism) {
            if (!selectedSiteSpecialism.contains(option)) {
                BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(option));
            }
        }
    }

    private List<String> getCurrentlySelectedSiteSpecialism() {
        // Retrieve elements representing selected site specialism
        List<WebElement> selectedSiteSpecialismElements = siteManagementPage.alreadySelectedSiteSpecialism;

        List<String> selectedSiteSpecialism = new ArrayList<>();
        for (WebElement element : selectedSiteSpecialismElements) {
            selectedSiteSpecialism.add(element.getText());
        }
        return selectedSiteSpecialism;
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(siteManagementPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(siteManagementPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Site management information is not updated!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(siteManagementPage.successMessage, 20);
    }
}
