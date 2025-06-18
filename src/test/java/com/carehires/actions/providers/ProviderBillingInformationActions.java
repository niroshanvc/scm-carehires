package com.carehires.actions.providers;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProviderBillingInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import com.carehires.utils.WebDriverInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProviderBillingInformationActions {

    ProviderBillingInformationPage billingInformationPage;
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();
    GenericUtils genericUtils;

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String YML_FILE_SMOKE = "provider-create-smoke";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_FILE_PROVIDER = "provider-user-update-organization";
    private static final String YML_HEADER = "Billing Information";
    private static final String YML_HEADER_SUB = "General Billing Information";
    private static final String YML_HEADER_SUB2 = "Custom Billing Information";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String PROVIDER_INCREMENT = "provider_incrementValue";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderBillingInformationActions.class);
    Integer incrementValue;

    {
        try {
            genericUtils = new GenericUtils();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverInitializationException("Failed to initialize WebDriver for GenericUtils", e);
        }
    }

    public ProviderBillingInformationActions() throws BasePage.WebDriverInitializationException {
        billingInformationPage = new ProviderBillingInformationPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), billingInformationPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new BasePage.WebDriverInitializationException("Failed to initialize WebDriver for GenericUtils", e);
        }
    }

    public void savingGeneralBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering General Billing Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable(PROVIDER_INCREMENT, Integer.class);

        // Log the retrieved value
        logger.info("Retrieved provider increment value in BillingInformation: %s", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);

        enterGeneralBillingDataWithoutPreferredPaymentPolicies(YML_FILE, ADD);
        enterPreferredPaymentPolicies(YML_FILE, ADD);

        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingInformationPage.saveButton);

        verifySuccessMessage();
    }

    private void enterGeneralBillingDataWithoutPreferredPaymentPolicies(String ymlFile, String subHeader) {
        String attentionTo = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB, subHeader, "AddressBillsInAttentionTo");
        BasePage.clearAndEnterTexts(billingInformationPage.addressBillsInAttentionTo, attentionTo);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                YML_HEADER, YML_HEADER_SUB, subHeader, "DigitalBillingAddress");
        BasePage.clearAndEnterTexts(billingInformationPage.digitalBillingAddress, digitalBillingAddress);

        String postalCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB, subHeader, "Postal Code");
        genericUtils.fillAddress(billingInformationPage.postalCode, postalCode, 300);

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB, subHeader, "PhoneNumber");
        BasePage.clearAndEnterTexts(billingInformationPage.phoneNumber, phone);

        String costCenter = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB, subHeader, "CostCenter");
        if (costCenter != null && !costCenter.trim().isEmpty()) {
            BasePage.clearAndEnterTexts(billingInformationPage.costCenter, costCenter);
        }
    }

    private void enterPreferredPaymentPolicies(String ymlFile, String subHeader) {
        BasePage.scrollToWebElement(billingInformationPage.saveButton);
        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB, subHeader, "BillingCycle");
        BasePage.clickWithJavaScript(billingInformationPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(billingInformationPage.getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB, subHeader, "CreditTerm");
        BasePage.clickWithJavaScript(billingInformationPage.creditTermDropdown);
        BasePage.clickWithJavaScript(billingInformationPage.getDropdownOptionXpath(creditTerm));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(billingInformationPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(billingInformationPage.successMessage).toLowerCase().trim();
        String expected = "Billing info added successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Billing information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(billingInformationPage.successMessage, 20);
    }

    public void updatingGeneralBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering General Billing Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable(PROVIDER_INCREMENT, Integer.class);
        navigationMenu.gotoBillingPage();
        BasePage.waitUntilPageCompletelyLoaded();

        // enter general billing information
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);
        enterGeneralBillingDataWithoutPreferredPaymentPolicies(EDIT_YML_FILE, ADD);
        logger.info("Entered General Billing Information without Preferred Payment Policies");
        enterPreferredPaymentPolicies(EDIT_YML_FILE, ADD);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(billingInformationPage.saveButton);
        verifySuccessMessage();

        // updating general billing information
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating General Billing Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);
        navigationMenu.gotoBillingPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);
        enterGeneralBillingDataWithoutPreferredPaymentPolicies(EDIT_YML_FILE, UPDATE);
        String creditLimit = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "CreditLimitAmount");
        BasePage.clearAndEnterTexts(billingInformationPage.creditLimitAmount, creditLimit);
        enterPreferredPaymentPolicies(EDIT_YML_FILE, UPDATE);

        // enter Billing Contact Details
        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "FirstName");
        BasePage.clearAndEnterTexts(billingInformationPage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "LastName");
        BasePage.clearAndEnterTexts(billingInformationPage.lastName, lastName);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "EmailAddress");
        BasePage.clearAndEnterTexts(billingInformationPage.emailAddress, email);

        String contactPhone = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "PhoneNumberContact");
        BasePage.clearAndEnterTexts(billingInformationPage.phoneNumberBillingContact, contactPhone);

        BasePage.genericWait(8000);
        BasePage.waitUntilElementClickable(billingInformationPage.saveButton, 60);
        BasePage.clickWithoutWaiting(billingInformationPage.saveButton);
    }


    public void savingGeneralAndCustomBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< First entering General Billing Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable(PROVIDER_INCREMENT, Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.genericWait(5000);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);
        enterGeneralBillingDataWithoutPreferredPaymentPolicies(YML_FILE, ADD);
        enterPreferredPaymentPolicies(YML_FILE, ADD);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(billingInformationPage.saveButton);

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Then entering Custom Billing Information >>>>>>>>>>>>>>>>>>>>");
        navigationMenu.gotoBillingPageUsingCircleIcon();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(billingInformationPage.customBillingInformationSection);
        enterCustomBillingData();
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(billingInformationPage.customSaveButton);

        //wait until custom billing saved
        BasePage.waitUntilElementClickable(billingInformationPage.customBillingEditIcon, 60);
        BasePage.clickWithJavaScript(billingInformationPage.continueButton);

    }

    private void enterCustomBillingData() {
        BasePage.genericWait(5000);
        String siteName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Site");
        BasePage.waitUntilElementPresent(billingInformationPage.siteDropdown, 30);
        BasePage.clickWithJavaScript(billingInformationPage.siteDropdown);
        BasePage.waitUntilElementClickable(billingInformationPage.getDropdownOptionXpath(siteName), 30);
        BasePage.clickWithJavaScript(billingInformationPage.getDropdownOptionXpath(siteName));

        String attentionTo = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Address bills in attention to");
        BasePage.clearAndEnterTexts(billingInformationPage.customAddressBillsInAttentionTo, attentionTo);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE,
                YML_HEADER, YML_HEADER_SUB2, ADD, "Digital Billing Address");
        BasePage.clearAndEnterTexts(billingInformationPage.customDigitalBillingAddress, digitalBillingAddress);

        String postalCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Postal Code");
        genericUtils.fillAddress(billingInformationPage.customBillingPostalCode, postalCode, 300);

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Phone Number");
        BasePage.clearAndEnterTexts(billingInformationPage.customPhoneNumber, phone);

        String costCenter = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Cost Center");
        if (costCenter != null && !costCenter.trim().isEmpty()) {
            BasePage.clearAndEnterTexts(billingInformationPage.customCostCenter, costCenter);
        }

        BasePage.scrollToWebElement(billingInformationPage.customCreditTermDropdown);
        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Billing Cycle");
        BasePage.clickWithJavaScript(billingInformationPage.customBillingCycleDropdown);
        BasePage.clickWithJavaScript(billingInformationPage.getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                YML_HEADER_SUB2, ADD, "Credit Term");
        BasePage.clickWithJavaScript(billingInformationPage.customCreditTermDropdown);
        BasePage.clickWithJavaScript(billingInformationPage.getDropdownOptionXpath(creditTerm));
    }

    private void fillGeneralBillingBankInfo(String ymlFile, String subHeader) {
        String generalBilling = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB2, subHeader, "Is same as general billing bank infor");
        assert generalBilling != null;
        if (generalBilling.equalsIgnoreCase("Yes")) {
            String text = BasePage.getAttributeValue(billingInformationPage.isSameAsGeneralBillingBankInformationSpan,
                    "class");
            if (!text.contains("checked")) {
                BasePage.clickWithJavaScript(billingInformationPage.isSameAsGeneralBillingBankInformationCheckbox);
            }

        } else {
            String accountName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_HEADER_SUB2, subHeader, "Account Name");
            BasePage.clearAndEnterTexts(billingInformationPage.accountName, accountName);

            String accountNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_HEADER_SUB2, subHeader, "Account Number");
            BasePage.clearAndEnterTexts(billingInformationPage.accountNumber, accountNumber);

            String sortCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_HEADER_SUB2, subHeader, "Sort Code");
            BasePage.clearAndEnterTexts(billingInformationPage.sortCode, sortCode);
        }
    }

    public void updatingBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Updating General Billing Information - In Edit >>>>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable(PROVIDER_INCREMENT, Integer.class);
        navigationMenu.gotoBillingPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);
        enterGeneralBillingDataWithoutPreferredPaymentPolicies(YML_FILE_PROVIDER, UPDATE);
        enterPreferredPaymentPolicies(YML_FILE_PROVIDER, UPDATE);

        // enter Billing Contact Details
        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_PROVIDER, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "FirstName");
        BasePage.clearAndEnterTexts(billingInformationPage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_PROVIDER, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "LastName");
        BasePage.clearAndEnterTexts(billingInformationPage.lastName, lastName);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_PROVIDER, YML_HEADER,
                YML_HEADER_SUB, UPDATE, "EmailAddress");
        BasePage.clearAndEnterTexts(billingInformationPage.emailAddress, email);

        String contactPhone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_PROVIDER,
                YML_HEADER, YML_HEADER_SUB, UPDATE, "PhoneNumberContact");
        BasePage.clearAndEnterTexts(billingInformationPage.phoneNumberBillingContact, contactPhone);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(billingInformationPage.saveButton);
        verifyUpdateSuccessMessage();
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(billingInformationPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(billingInformationPage.successMessage).toLowerCase().trim();
        String expected = "Billing information updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Billing information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(billingInformationPage.successMessage, 20);
    }

    public void savingGeneralBillingInformationForSmoke() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering General Billing Information >>>>>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable(PROVIDER_INCREMENT, Integer.class);

        // Log the retrieved value
        logger.info("< Retrieved provider increment value in BillingInformation: %s", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);

        enterGeneralBillingDataWithoutPreferredPaymentPolicies(YML_FILE_SMOKE, ADD);
        enterPreferredPaymentPolicies(YML_FILE_SMOKE, ADD);

        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingInformationPage.saveButton);

        verifySuccessMessage();
    }
}