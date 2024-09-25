package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateProvidersSiteManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateProvidersSiteManagementActions {

    CreateProvidersSiteManagementPage siteManagementPage;
    GenericUtils genericUtils =  new GenericUtils();

    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "SiteManagement";
    private static final Logger logger = LogManager.getFormatterLogger(CreateProvidersSiteManagementActions.class);

    public CreateProvidersSiteManagementActions() {
        siteManagementPage = new CreateProvidersSiteManagementPage();
        PageFactory.initElements(BasePage.getDriver(), siteManagementPage);
    }

    public void enterSiteManagementData() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(siteManagementPage.addNewButton);

        logger.info("Entering Site Name");
        String siteName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SiteName");
        BasePage.typeWithStringBuilder(siteManagementPage.siteName, siteName);

        logger.info("Entering Site Type");
        String siteType = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SiteType");
        BasePage.clickWithJavaScript(siteManagementPage.siteTypeDropdown);
        BasePage.clickWithJavaScript(getDropdownXpath(siteType));

        logger.info("Entering Site Specialism");
        String[] siteSpecialism = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SiteSpecialism").split(",");
        BasePage.clickWithJavaScript(siteManagementPage.siteSpecialismMultiSelectDropdown);
        BasePage.genericWait(500);
        for (String specialism : siteSpecialism) {
            BasePage.clickWithJavaScript(getMultiSelectDropdownXpath(specialism));
        }

        logger.info("Entering Site also known as data");
        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "AlsoKnownAs");
        BasePage.clickWithJavaScript(siteManagementPage.alsoKnownAs);
        BasePage.typeWithStringBuilder(siteManagementPage.alsoKnownAs, alsoKnownAs);

        logger.info("Entering Site Email");
        String siteEmail = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SiteEmail");
        BasePage.typeWithStringBuilder(siteManagementPage.siteEmail, siteEmail);

        logger.info("Entering Site Address");
        String postalCode = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PostalCode");
        genericUtils.fillAddress(siteManagementPage.postalCode, postalCode);

        logger.info("Entering Site No of beds");
        String numberOfBeds = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "NoOfBeds");
        BasePage.typeWithStringBuilder(siteManagementPage.noOfBeds, numberOfBeds);

        logger.info("Entering Site Code");
        String siteCode = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SiteCode");
        BasePage.typeWithStringBuilder(siteManagementPage.siteCode, siteCode);

        logger.info("Entering Site Phone Number");
        BasePage.clickWithJavaScript(siteManagementPage.selectPhoneDropdown);
        fillPhoneNumber(siteManagementPage.phoneNumber);

        logger.info("Entering Site job notification email address");
        BasePage.scrollToWebElement(siteManagementPage.addButton);
        String jobNotification = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "JobNotificationAddress");
        BasePage.typeWithStringBuilder(siteManagementPage.jobNotificationAddress, jobNotification);

        logger.info("Entering site approval notification email address");
        String approvalNotification = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "ApprovalNotificationAddress");
        BasePage.typeWithStringBuilder(siteManagementPage.approvalNotificationAddress, approvalNotification);

        BasePage.clickWithJavaScript(siteManagementPage.addButton);
        isSiteSaved();

        BasePage.clickWithJavaScript(siteManagementPage.updateButton);
        BasePage.waitUntilElementClickable(siteManagementPage.nextButton, 90);
        BasePage.clickWithJavaScript(siteManagementPage.nextButton);
    }

    private void isSiteSaved() {
        BasePage.waitUntilElementPresent(siteManagementPage.siteNameAddress, 90);
        String actualSiteName = BasePage.getText(siteManagementPage.siteNameAddress);
        String expectedSiteName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "SiteName");
        assertThat("Site is not saved", actualSiteName, is(expectedSiteName));
    }

    private String getDropdownXpath(String text) {
        return String.format("//nb-option//div[contains(text(),'%s')]", text);
    }

    private String getMultiSelectDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    //select value from 'Select Phone' dropdown and enter phone number
    private void fillPhoneNumber(WebElement phoneNumberInput) {
        logger.info("fillPhoneNumber");
        String phoneNumber = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PhoneNumber");
        String phoneType = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PhoneType");

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
}
