package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.BillingProfileManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BillingProfileManagementActions {

    private final BillingProfileManagementPage billingPage;
    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Billing Profile Management";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final Logger logger = LogManager.getLogger(BillingProfileManagementActions.class);

    public BillingProfileManagementActions() {
        billingPage = new BillingProfileManagementPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), billingPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBilling() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering billing information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("agency_incrementValue", Integer.class);

        // Log the retrieved value
        logger.info("Retrieved agency increment value in BillingProfile: {}", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for agency is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        enterBillingData(YML_FILE, ADD);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingPage.saveButton);
        waitUntilTwoBalloonPopupGetDisappeared();
    }

    private void enterBillingData(String ymlFile, String subHeader) {
        String addressBills = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AttentionTo");
        BasePage.typeWithStringBuilder(billingPage.addressBillsInAttentionTo, addressBills);

        String billingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "BillingAddress");
        BasePage.typeWithStringBuilder(billingPage.billingAddress, billingAddress);

        //unique number
        String costCenter = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CostCenter");
        BasePage.typeWithStringBuilder(billingPage.costCenter, costCenter);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "DigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingPage.digitalBillingAddress, digitalBillingAddress);

        String ccDigitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CCDigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingPage.ccDigitalBillingAddress, ccDigitalBillingAddress);

        String phoneNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "PhoneNumber");
        BasePage.typeWithStringBuilder(billingPage.phoneNumber, phoneNumber);

        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "BillingCycle");
        BasePage.scrollToWebElement(billingPage.sortCode);
        BasePage.clickWithJavaScript(billingPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(billingPage.getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CreditTerm");
        BasePage.clickWithJavaScript(billingPage.creditTermDropdown);
        BasePage.clickWithJavaScript(billingPage.getDropdownOptionXpath(creditTerm));

        String bankName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "BankName");
        BasePage.typeWithStringBuilder(billingPage.bankName, bankName);

        String accountNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AccountNumber");
        BasePage.typeWithStringBuilder(billingPage.accountNumber, accountNumber);

        String accountName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AccountName");
        BasePage.typeWithStringBuilder(billingPage.accountName, accountName);

        String sortCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "SortCode");
        BasePage.typeWithStringBuilder(billingPage.sortCode, sortCode);
    }

    private void waitUntilTwoBalloonPopupGetDisappeared() {
        WebDriverWait wait = BasePage.webdriverWait(30);

        // Wait for the first popup to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(billingPage.getFirstSuccessMessage()));

        // Wait for the second popup to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(billingPage.getSecondSuccessMessage()));
    }

    public void editBillingInfo() {
        navigateToBillingInfoPage();

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Editing billing information >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        enterBillingData(EDIT_YML_FILE, UPDATE);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingPage.saveButton);
        waitUntilTwoBalloonPopupGetDisappeared();
    }

    private void navigateToBillingInfoPage() {
        navigationMenu.gotoBillingPage();
        BasePage.genericWait(2000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Agency Billing >>>>>>>>>>>>>>>>>>>>");
        if (BasePage.isElementDisplayed(billingPage.updateButton)) {
            BasePage.clickWithJavaScript(billingPage.updateButton);
        }
        BasePage.waitUntilElementPresent(billingPage.nextButton, 30);
        BasePage.genericWait(1000);
    }
}
