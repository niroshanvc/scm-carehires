package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateProviderBillingInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateProviderBillingInformationActions {

    CreateProviderBillingInformationPage billingInformationPage;

    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "BillingInformation";
    private static final String YML_HEADER_SUB = "GeneralBillingInformation";
    private static final Logger logger = LogManager.getFormatterLogger(CreateProviderBillingInformationActions.class);

    public CreateProviderBillingInformationActions() {
        billingInformationPage = new CreateProviderBillingInformationPage();
        PageFactory.initElements(BasePage.getDriver(), billingInformationPage);
    }

    public void fillGeneralBillingInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering General Billing Information >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        String attentionTo = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "AddressBillsInAttentionTo");
        BasePage.typeWithStringBuilder(billingInformationPage.addressBillsInAttentionTo, attentionTo);

        String billingAddress = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "BillingAddress");
        BasePage.typeWithStringBuilder(billingInformationPage.billingAddress, billingAddress);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "DigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingInformationPage.digitalBillingAddress, digitalBillingAddress);

        String phone = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "PhoneNumber");
        BasePage.typeWithStringBuilder(billingInformationPage.phoneNumber, phone);

        String creditLimit = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "CreditLimitAmount");
        BasePage.typeWithStringBuilder(billingInformationPage.creditLimitAmount, creditLimit);

        BasePage.scrollToWebElement(billingInformationPage.saveButton);
        String billingCycle = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "BillingCycle");
        BasePage.clickWithJavaScript(billingInformationPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "CreditTerm");
        BasePage.clickWithJavaScript(billingInformationPage.creditTermDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(creditTerm));

        String firstName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "FirstName");
        BasePage.typeWithStringBuilder(billingInformationPage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "LastName");
        BasePage.typeWithStringBuilder(billingInformationPage.lastName, lastName);

        String email = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "EmailAddress");
        BasePage.typeWithStringBuilder(billingInformationPage.emailAddress, email);

        String contactPhone = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, YML_HEADER_SUB, "PhoneNumberContact");
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
    }
}