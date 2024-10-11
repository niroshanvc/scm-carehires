package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.CreateBillingProfileManagementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateBillingProfileManagementActions {

    CreateBillingProfileManagementPage billingPage;

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "BillingProfileManagement";
    private static final Logger logger = LogManager.getLogger(CreateBillingProfileManagementActions.class);

    public CreateBillingProfileManagementActions() {
        billingPage = new CreateBillingProfileManagementPage();
        PageFactory.initElements(BasePage.getDriver(), billingPage);
    }

    public void addBilling() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering billing information >>>>>>>>>>>>>>>>>>>>");

        // Use the increment value retrieved in the Hooks
        int incrementValue = GlobalVariables.getVariable("incrementValue", Integer.class);

        BasePage.waitUntilPageCompletelyLoaded();
        String addressBills = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "AttentionTo");
        BasePage.typeWithStringBuilder(billingPage.addressBillsInAttentionTo, addressBills);

        String billingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "BillingAddress");
        BasePage.typeWithStringBuilder(billingPage.billingAddress, billingAddress);

        //unique number
        String costCenter = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CostCenter");
        BasePage.typeWithStringBuilder(billingPage.costCenter, costCenter);

        String digitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingPage.digitalBillingAddress, (digitalBillingAddress + incrementValue));

        String ccDigitalBillingAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CCDigitalBillingAddress");
        BasePage.typeWithStringBuilder(billingPage.ccDigitalBillingAddress, (ccDigitalBillingAddress + incrementValue));

        String phoneNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "PhoneNumber");
        BasePage.typeWithStringBuilder(billingPage.phoneNumber, phoneNumber);

        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "BillingCycle");
        BasePage.scrollToWebElement(billingPage.sortCode);
        BasePage.clickWithJavaScript(billingPage.billingCycleDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(billingCycle));

        String creditTerm = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CreditTerm");
        BasePage.clickWithJavaScript(billingPage.creditTermDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(creditTerm));

        String bankName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "BankName");
        BasePage.typeWithStringBuilder(billingPage.bankName, bankName);

        String accountNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "AccountNumber");
        BasePage.typeWithStringBuilder(billingPage.accountNumber, accountNumber);

        String accountName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "AccountName");
        BasePage.typeWithStringBuilder(billingPage.accountName, accountName);

        String sortCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "SortCode");
        BasePage.typeWithStringBuilder(billingPage.sortCode, sortCode);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(billingPage.saveButton);

        BasePage.genericWait(3000);
        isBasicInfoSaved();
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    //verify if basic information is saved
    private void isBasicInfoSaved() {
        List<WebElement> allElements = BasePage.findListOfWebElements(CreateBillingProfileManagementPage.BASIC_INFORMATION_SUB_XPATHS);

        //filter the elements that have an 'id' attribute
        List<WebElement> elementsWithIdAttribute = allElements.stream()
                .filter(element -> element.getAttribute("id") != null && !Objects.requireNonNull(element.getAttribute("id")).isEmpty())
                .toList();

        //check if any of the elements have an 'id' attribute equal to 'Icon_material-done'
        boolean hasIdDone = elementsWithIdAttribute.stream()
                .anyMatch(element -> Objects.equals(element.getAttribute("id"), "Icon_material-done"));

        assertThat("Billing information is not saved",hasIdDone, is(true));
    }
}
