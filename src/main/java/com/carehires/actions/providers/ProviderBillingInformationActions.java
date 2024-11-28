package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProviderBillingInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProviderBillingInformationActions {

    ProviderBillingInformationPage billingInformationPage;
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_HEADER = "Billing Information";
    private static final String YML_HEADER_SUB = "General Billing Information";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderBillingInformationActions.class);
    Integer incrementValue;

    public ProviderBillingInformationActions() {
        billingInformationPage = new ProviderBillingInformationPage();
        PageFactory.initElements(BasePage.getDriver(), billingInformationPage);
    }

    public void savingGeneralBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering General Billing Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);

        enterGeneralBillingData(YML_FILE, ADD);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingInformationPage.saveButton);

        verifySuccessMessage();
    }

    private void enterGeneralBillingData(String ymlFile, String subHeader) {
        String attentionTo = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "AddressBillsInAttentionTo");
        BasePage.clearAndEnterTexts(billingInformationPage.addressBillsInAttentionTo, attentionTo);

        String billingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "BillingAddress");
        BasePage.clearAndEnterTexts(billingInformationPage.billingAddress, billingAddress);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "DigitalBillingAddress");
        BasePage.clearAndEnterTexts(billingInformationPage.digitalBillingAddress, digitalBillingAddress);

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "PhoneNumber");
        BasePage.clearAndEnterTexts(billingInformationPage.phoneNumber, phone);

        String costCenter = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "CostCenter");
        if (costCenter != null && !costCenter.trim().isEmpty()) {
            BasePage.clearAndEnterTexts(billingInformationPage.costCenter, costCenter);
        }

        BasePage.scrollToWebElement(billingInformationPage.saveButton);
        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "BillingCycle");
        BasePage.clickWithJavaScript(billingInformationPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB, subHeader, "CreditTerm");
        BasePage.clickWithJavaScript(billingInformationPage.creditTermDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(creditTerm));
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
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
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);
        navigationMenu.gotoBillingPage();
        BasePage.waitUntilPageCompletelyLoaded();

        // enter general billing information
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);
        enterGeneralBillingData(EDIT_YML_FILE, ADD);
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
        enterGeneralBillingData(EDIT_YML_FILE, UPDATE);

        // enter Billing Contact Details
        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, YML_HEADER_SUB, UPDATE, "FirstName");
        BasePage.clearAndEnterTexts(billingInformationPage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, YML_HEADER_SUB, UPDATE, "LastName");
        BasePage.clearAndEnterTexts(billingInformationPage.lastName, lastName);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, YML_HEADER_SUB, UPDATE, "EmailAddress");
        BasePage.clearAndEnterTexts(billingInformationPage.emailAddress, email);

        String contactPhone = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, YML_HEADER_SUB, UPDATE, "PhoneNumberContact");
        BasePage.clearAndEnterTexts(billingInformationPage.phoneNumberBillingContact, contactPhone);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(billingInformationPage.saveButton);
        verifySuccessMessageInEditMode();
    }

    private void verifySuccessMessageInEditMode() {
        BasePage.waitUntilElementPresent(billingInformationPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(billingInformationPage.successMessage).toLowerCase().trim();
        String expected = "Billing information updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Edit Billing information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(billingInformationPage.successMessage, 20);
    }
}