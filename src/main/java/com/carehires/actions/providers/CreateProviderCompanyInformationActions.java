package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateProviderCompanyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateProviderCompanyInformationActions {
    
    CreateProviderCompanyInformationPage companyInformationPage;
    GenericUtils genericUtils = new GenericUtils();

    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "CompanyInformation";
    private static final String FOLDER_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Provider\\";

    private static final Logger logger = LogManager.getLogger(CreateProviderCompanyInformationActions.class);
    
    public CreateProviderCompanyInformationActions() {
        companyInformationPage =  new CreateProviderCompanyInformationPage();
        PageFactory.initElements(BasePage.getDriver(), companyInformationPage);
    }

    public void enterCompanyInformation() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Company Information >>>>>>>>>>>>>>>>>>>>");

        //upload a logo
        logger.info("Uploading a logo");
        String providerLogo = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "ProviderLogo");
        String absoluteFilePath = FOLDER_PATH + providerLogo;
        BasePage.clickWithJavaScript(companyInformationPage.uploadLogo);
        BasePage.uploadFile(companyInformationPage.fileInputButton, absoluteFilePath);
        BasePage.clickWithJavaScript(companyInformationPage.imageSaveButton);

        logger.info("Entering company name");
        String companyName = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "CompanyName");
        BasePage.typeWithStringBuilder(companyInformationPage.companyName, companyName);

        logger.info("Entering business registration number");
        String businessRegNum = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "BusinessRegistrationNumber");
        BasePage.typeWithStringBuilder(companyInformationPage.businessRegistrationNumber, businessRegNum);

        logger.info("Entering company type");
        String companyType = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "CompanyType");
        BasePage.clickWithJavaScript(companyInformationPage.companyTypeDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(companyType));

        logger.info("Entering company website");
        String website = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "Website");
        BasePage.typeWithStringBuilder(companyInformationPage.website, website);

        //enter postcode and select a valid address
        logger.info("Entering postcode");
        String providerPostcode = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "ProviderPostCode");
        genericUtils.fillAddress(companyInformationPage.postcode, providerPostcode);

        //enter phone number
        logger.info("Entering phone number");
        genericUtils.fillPhoneNumber(YML_FILE, YML_HEADER, "PhoneNumber", companyInformationPage.phoneNumberInput);
        BasePage.clickTabKey(companyInformationPage.phoneNumberInput);

        logger.info("Entering VAT information");
        String vatRegistered = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AreYouVatRegistered");
        if (vatRegistered.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredYes);
            BasePage.waitUntilElementPresent(companyInformationPage.vatNumber, 10);
            String vatNumber = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "VatNo");
            BasePage.typeWithStringBuilder(companyInformationPage.vatNumber, vatNumber);

            String vatRegDoc = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "VatRegisteredDocumentName");
            String absoluteFilePathVatRegDoc = FOLDER_PATH + vatRegDoc;
            BasePage.uploadFile(companyInformationPage.vatRegisteredDocument, absoluteFilePathVatRegDoc);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredNo);
        }

        BasePage.scrollToWebElement(companyInformationPage.numberOfEmployeeUnderFifty);

        logger.info("Entering VAT exemption information");
        String vatExempt = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AreYouVatExempt");
        if (vatExempt.equalsIgnoreCase("yes")) {
            BasePage.waitUntilElementClickable(companyInformationPage.vatExemptYes, 30);
            BasePage.clickWithJavaScript(companyInformationPage.vatExemptYes);
            String vatExemptDoc = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "VatExemptDocumentName");
            String absoluteFilePathVatExemptDoc = FOLDER_PATH + vatExemptDoc;
            BasePage.genericWait(100);
            BasePage.uploadFile(companyInformationPage.vatExemptDocument, absoluteFilePathVatExemptDoc);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.vatExemptNo);
        }

        logger.info("Entering annual company turnover");
        String annualCompanyTurnOver = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AnnualCompanyTurnOverIs");
        if (annualCompanyTurnOver.equalsIgnoreCase("Under 10.2M")) {
            BasePage.waitUntilElementClickable(companyInformationPage.annualCompanyTurnOverUnderTen, 30);
            BasePage.clickWithJavaScript(companyInformationPage.annualCompanyTurnOverUnderTen);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.annualCompanyTurnOverOverTen);
        }

        logger.info("Entering company balance sheet");
        String companyBalanceSheet = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "CompanyBalanceSheetTotalIs");
        if (companyBalanceSheet.equalsIgnoreCase("Under 5.2M")) {
            BasePage.clickWithJavaScript(companyInformationPage.balanceSheetTotalUnderFive);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.balanceSheetTotalOverFive);
        }

        logger.info("Entering number of employees");
        String averageNumEmp = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AverageNumberOfEmployees");
        if (averageNumEmp.equalsIgnoreCase("Under 50")) {
            BasePage.clickWithJavaScript(companyInformationPage.numberOfEmployeeUnderFifty);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.numberOfEmployeeOverFifty);
        }

        BasePage.clickWithJavaScript(companyInformationPage.saveButton);
        verifySuccessMessage();
        BasePage.waitUntilElementClickable(companyInformationPage.updateButton, 120);
        isBasicInfoSaved();
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    //verify if basic information is saved
    private void isBasicInfoSaved() {
        List<WebElement> allElements = BasePage.findListOfWebElements(CreateProviderCompanyInformationPage.COMPANY_INFORMATION_SUB_XPATHS);

        //filter the elements that have an 'id' attribute
        List<WebElement> elementsWithIdAttribute = allElements.stream()
                .filter(element -> element.getAttribute("id") != null && !Objects.requireNonNull(element.getAttribute("id")).isEmpty())
                .toList();

        //check if any of the elements have an 'id' attribute equal to 'Icon_material-done'
        boolean hasIdDone = elementsWithIdAttribute.stream()
                .anyMatch(element -> Objects.equals(element.getAttribute("id"), "Icon_material-done"));

        assertThat("Basic information is not saved",hasIdDone, is(true));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(companyInformationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(companyInformationPage.successMessage).toLowerCase().trim();
        String expected = "Data saved Successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Company information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(companyInformationPage.successMessage, 20);
    }
}
