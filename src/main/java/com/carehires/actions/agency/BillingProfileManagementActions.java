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
    private static final Logger logger = LogManager.getLogger(BillingProfileManagementActions.class);

    public BillingProfileManagementActions() {
        billingPage = new BillingProfileManagementPage();
        PageFactory.initElements(BasePage.getDriver(), billingPage);
    }

    public void addBilling() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering billing information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        enterBillingData(YML_FILE);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingPage.saveButton);
        waitUntilTwoBalloonPopupGetDisappeared();
    }

    private void enterBillingData(String ymlFile) {
        String addressBills = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "AttentionTo");
        BasePage.typeWithStringBuilder(billingPage.addressBillsInAttentionTo, addressBills);

        String billingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "BillingAddress");
        BasePage.typeWithStringBuilder(billingPage.billingAddress, billingAddress);

        //unique number
        String costCenter = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "CostCenter");
        BasePage.typeWithStringBuilder(billingPage.costCenter, costCenter);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "DigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingPage.digitalBillingAddress, digitalBillingAddress);

        String ccDigitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "CCDigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingPage.ccDigitalBillingAddress, ccDigitalBillingAddress);

        String phoneNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "PhoneNumber");
        BasePage.typeWithStringBuilder(billingPage.phoneNumber, phoneNumber);

        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "BillingCycle");
        BasePage.scrollToWebElement(billingPage.sortCode);
        BasePage.clickWithJavaScript(billingPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "CreditTerm");
        BasePage.clickWithJavaScript(billingPage.creditTermDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(creditTerm));

        String bankName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "BankName");
        BasePage.typeWithStringBuilder(billingPage.bankName, bankName);

        String accountNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "AccountNumber");
        BasePage.typeWithStringBuilder(billingPage.accountNumber, accountNumber);

        String accountName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "AccountName");
        BasePage.typeWithStringBuilder(billingPage.accountName, accountName);

        String sortCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "SortCode");
        BasePage.typeWithStringBuilder(billingPage.sortCode, sortCode);
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
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
        enterBillingData(EDIT_YML_FILE);
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
