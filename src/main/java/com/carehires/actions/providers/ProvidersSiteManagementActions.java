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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProvidersSiteManagementActions {

    private final ProvidersSiteManagementPage siteManagementPage;
    private static final GenericUtils genericUtils =  new GenericUtils();
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_HEADER = "Site Management";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final Logger logger = LogManager.getFormatterLogger(ProvidersSiteManagementActions.class);
    Integer incrementValue;

    public ProvidersSiteManagementActions() {
        siteManagementPage = new ProvidersSiteManagementPage();
        PageFactory.initElements(BasePage.getDriver(), siteManagementPage);
    }

    public void addSiteManagementData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Site Management Information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(siteManagementPage.addNewButton);

        enterSiteManagementData(YML_FILE, ADD);

        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(siteManagementPage.addButton);
        verifySuccessMessage();
        isSiteSaved(YML_FILE, ADD);

        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        BasePage.waitUntilElementClickable(siteManagementPage.nextButton, 90);
        BasePage.clickWithJavaScript(siteManagementPage.nextButton);
    }

    private void enterSiteManagementData(String ymlFile, String subHeader) {
        logger.info("Entering Site Name");
        String siteName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SiteName");
        BasePage.clearAndEnterTexts(siteManagementPage.siteName, siteName);

        logger.info("Entering Site Type");
        String siteType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SiteType");
        BasePage.clickWithJavaScript(siteManagementPage.siteTypeDropdown);
        BasePage.waitUntilElementClickable(getDropdownXpath(siteType), 30);
        BasePage.clickWithJavaScript(getDropdownXpath(siteType));

        logger.info("Entering Site Specialism");
        String[] siteSpecialism = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SiteSpecialism").split(",");
        BasePage.clickWithJavaScript(siteManagementPage.siteSpecialismMultiSelectDropdown);
        BasePage.genericWait(500);
        for (String specialism : siteSpecialism) {
            BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(specialism));
        }

        logger.info("Entering Site also known as data");
        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AlsoKnownAs");
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);
        BasePage.clearAndEnterTexts(siteManagementPage.alsoKnownAs, alsoKnownAs);

        logger.info("Entering Site Email");
        String siteEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SiteEmail");
        BasePage.clearAndEnterTexts(siteManagementPage.siteEmail, siteEmail);

        logger.info("Entering Site Address");
        String postalCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "PostalCode");
        genericUtils.fillAddress(siteManagementPage.postalCode, postalCode);

        logger.info("Entering Site No of beds");
        String numberOfBeds = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "NoOfBeds");
        BasePage.clearAndEnterTexts(siteManagementPage.noOfBeds, numberOfBeds);

        logger.info("Entering Site Code");
        String siteCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SiteCode");
        BasePage.clearAndEnterTexts(siteManagementPage.siteCode, siteCode);

        logger.info("Entering Site Phone Number");
        BasePage.clickWithJavaScript(siteManagementPage.selectPhoneDropdown);
        fillPhoneNumber(siteManagementPage.phoneNumber, ymlFile, subHeader);

        logger.info("Entering Site job notification email address");
        BasePage.scrollToWebElement(siteManagementPage.approvalNotificationAddress);
        String jobNotification = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "JobNotificationAddress");
        BasePage.clearAndEnterTexts(siteManagementPage.jobNotificationAddress, jobNotification);

        logger.info("Entering site approval notification email address");
        String approvalNotification = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "ApprovalNotificationAddress");
        BasePage.clearAndEnterTexts(siteManagementPage.approvalNotificationAddress, approvalNotification);
    }

    private void isSiteSaved(String ymlFile, String subHeader) {
        BasePage.waitUntilElementPresent(siteManagementPage.siteNameAddress, 90);
        String actualSiteName = BasePage.getText(siteManagementPage.siteNameAddress).trim();
        String expectedSiteName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SiteName");
        assertThat("Site is not saved", actualSiteName, is(expectedSiteName));
    }

    private String getDropdownXpath(String text) {
        return String.format("//nb-option//div[contains(text(),'%s')]", text);
    }

    private String getMultiSelectDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    //select value from 'Select Phone' dropdown and enter phone number
    private void fillPhoneNumber(WebElement phoneNumberInput, String ymlFile, String subHeader) {
        logger.info("fillPhoneNumber");
        String phoneNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "PhoneNumber");
        String phoneType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "PhoneType");

        switch (phoneType) {
            case "Out of office":
                BasePage.clickWithJavaScript(siteManagementPage.outOfOfficeOption);
                break;
            case "Other":
                BasePage.clickWithJavaScript(siteManagementPage.otherOption);
                break;
            case "Office2":
                BasePage.clickWithJavaScript(siteManagementPage.office2Option);
                break;
            case "Office1":
                BasePage.clickWithJavaScript(siteManagementPage.office1Option);
                break;
            case "Mobile":
                BasePage.clickWithJavaScript(siteManagementPage.mobileOption);
                break;
            default:
                throw new IllegalArgumentException("Invalid phone number type");
        }
        BasePage.typeWithStringBuilder(phoneNumberInput, phoneNumber);
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
        enterSiteManagementData(EDIT_YML_FILE, ADD);
        BasePage.clickWithJavaScript(siteManagementPage.addButton);
        verifySuccessMessage();
        BasePage.waitUntilElementClickable(siteManagementPage.updateButton, 90);

        // edit already added site data
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Editing Site Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        BasePage.waitUntilElementDisplayed(siteManagementPage.editDetailsIcon, 30);
        BasePage.clickWithJavaScript(siteManagementPage.editDetailsIcon);
        enterSiteManagementData(EDIT_YML_FILE, UPDATE);
        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        verifyUpdateSuccessMessage();

        // click on next button
        BasePage.clickWithJavaScript(siteManagementPage.nextButton);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(siteManagementPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(siteManagementPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Site management information update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(siteManagementPage.successMessage, 20);
    }
}
