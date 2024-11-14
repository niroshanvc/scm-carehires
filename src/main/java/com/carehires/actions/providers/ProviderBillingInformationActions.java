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

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "BillingInformation";
    private static final String YML_HEADER_SUB = "GeneralBillingInformation";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderBillingInformationActions.class);

    public ProviderBillingInformationActions() {
        billingInformationPage = new ProviderBillingInformationPage();
        PageFactory.initElements(BasePage.getDriver(), billingInformationPage);
    }

    public void fillGeneralBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering General Billing Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(billingInformationPage.addressBillsInAttentionTo, 60);

        String attentionTo = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "AddressBillsInAttentionTo");
        BasePage.typeWithStringBuilder(billingInformationPage.addressBillsInAttentionTo, attentionTo);

        String billingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "BillingAddress");
        BasePage.typeWithStringBuilder(billingInformationPage.billingAddress, billingAddress);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "DigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingInformationPage.digitalBillingAddress, digitalBillingAddress);

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "PhoneNumber");
        BasePage.typeWithStringBuilder(billingInformationPage.phoneNumber, phone);

        String creditLimit = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "CreditLimitAmount");
        BasePage.typeWithStringBuilder(billingInformationPage.creditLimitAmount, creditLimit);

        BasePage.scrollToWebElement(billingInformationPage.saveButton);
        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "BillingCycle");
        BasePage.clickWithJavaScript(billingInformationPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "CreditTerm");
        BasePage.clickWithJavaScript(billingInformationPage.creditTermDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(creditTerm));

        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "FirstName");
        BasePage.typeWithStringBuilder(billingInformationPage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "LastName");
        BasePage.typeWithStringBuilder(billingInformationPage.lastName, lastName);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "EmailAddress");
        BasePage.typeWithStringBuilder(billingInformationPage.emailAddress, email);

        String contactPhone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB, "PhoneNumberContact");
        BasePage.typeWithStringBuilder(billingInformationPage.phoneNumberBillingContact, contactPhone);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingInformationPage.saveButton);

        verifySuccessMessage();
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(billingInformationPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(billingInformationPage.successMessage).toLowerCase().trim();
        String expected = "Billing info added successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Site management information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(billingInformationPage.successMessage, 20);
    }
}