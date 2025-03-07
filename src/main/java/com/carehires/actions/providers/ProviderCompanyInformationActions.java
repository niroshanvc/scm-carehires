package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProviderCompanyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import com.carehires.utils.WebDriverInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProviderCompanyInformationActions {
    
    ProviderCompanyInformationPage companyInformationPage;
    GenericUtils genericUtils;
    ProviderSiteManagementActions siteManagementActions;
    ProviderWorkerStaffActions workerStaffActions;
    ProviderUserManagementActions userManagementActions;
    ProviderBillingInformationActions billingInformationActions;
    ProviderSubContractingAgreementActions subContractingAgreementActions;

    {
        try {
            genericUtils = new GenericUtils();
        } catch (WebDriverInitializationException e) {
            throw new WebDriverInitializationException("Failed to initialize WebDriver for GenericUtils", e);
        }
    }

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_HEADER = "Company Information";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "resources" + File.separator + "Upload" + File.separator + "Provider"
            + File.separator;

    private static final Logger logger = LogManager.getLogger(ProviderCompanyInformationActions.class);

    public ProviderCompanyInformationActions() throws BasePage.WebDriverInitializationException {
        companyInformationPage =  new ProviderCompanyInformationPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), companyInformationPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new BasePage.WebDriverInitializationException("Failed to initialize WebDriver for GenericUtils", e);
        }
    }

    public void enterCompanyInformation() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Company Information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the current increment value for the provider (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);

        enterDataForInputFields(YML_FILE, ADD);

        BasePage.clickWithJavaScript(companyInformationPage.saveButton);
        verifySuccessMessage();
        BasePage.waitUntilElementClickable(companyInformationPage.updateButton, 120);
        isCompanyInfoSaved();

        // After successfully entering the company information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);

        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("provider_incrementValue", incrementValue);
    }

    private void enterDataForInputFields(String ymlFile, String subHeader) {
        uploadLogo(ymlFile, subHeader);
        enterBasicDetails(ymlFile, subHeader);
        enterAddressDetails(ymlFile, subHeader);
        enterPhoneNumber(ymlFile, subHeader);
        enterVatInformation(ymlFile, subHeader);
        enterVatExemption(ymlFile, subHeader);
        enterAnnualCompanyTurnover(ymlFile, subHeader);
        enterAverageNumberOfEmployees(ymlFile, subHeader);
    }

    private void uploadLogo(String ymlFile, String subHeader) {
        logger.info("Uploading a logo");
        String providerLogo = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "ProviderLogo");
        if (providerLogo != null) {
            try {
                String absoluteFilePath = FOLDER_PATH + providerLogo;
                BasePage.clickWithJavaScript(companyInformationPage.uploadLogo);
                BasePage.uploadFile(companyInformationPage.fileInputButton, absoluteFilePath);
                BasePage.clickWithJavaScript(companyInformationPage.imageSaveButton);
            } catch (NoSuchElementException e) {
                logger.info("Logo was not uploaded!");
            }
        }
    }

    private void enterBasicDetails(String ymlFile, String subHeader) {
        logger.info("Entering company name");
        String companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CompanyName");
        if (companyName != null) {
            BasePage.typeWithStringBuilder(companyInformationPage.companyName, companyName);
        }

        logger.info("Entering business registration number");
        String businessRegNum = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "BusinessRegistrationNumber");
        if (businessRegNum != null) {
            BasePage.clearAndEnterTexts(companyInformationPage.businessRegistrationNumber, businessRegNum);
        }

        logger.info("Entering company type");
        String companyType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CompanyType");
        if (companyType != null) {
            BasePage.clickWithJavaScript(companyInformationPage.companyTypeDropdown);
            BasePage.genericWait(500);
            BasePage.waitUntilElementPresent(companyInformationPage.getDropdownOptionXpath(companyType), 20);
            BasePage.clickWithJavaScript(companyInformationPage.getDropdownOptionXpath(companyType));
        }

        logger.info("Entering company website");
        String website = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "Website");
        if (website != null) {
            BasePage.typeWithStringBuilder(companyInformationPage.website, website);
        }
    }

    private void enterAddressDetails(String ymlFile, String subHeader) {
        logger.info("Entering postcode");
        String providerPostcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "ProviderPostCode");
        if (providerPostcode != null) {
            genericUtils.fillAddress(companyInformationPage.postcode, providerPostcode, 190);
        }
    }

    private void enterPhoneNumber(String ymlFile, String subHeader) {
        logger.info("Entering phone number");
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, companyInformationPage.phoneNumberInput, YML_HEADER, subHeader, "PhoneNumberType");
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, companyInformationPage.phoneNumberInput, YML_HEADER, subHeader, "PhoneNumber");
    }

    private void enterVatInformation(String ymlFile, String subHeader) {
        logger.info("Entering VAT information");
        String vatRegistered = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AreYouVatRegistered");
        if (vatRegistered != null) {
            if (vatRegistered.equalsIgnoreCase("yes")) {
                BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredYes);
                BasePage.waitUntilElementPresent(companyInformationPage.vatNumber, 10);
                String vatNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "VatNo");
                BasePage.clearAndEnterTexts(companyInformationPage.vatNumber, vatNumber);

                try {
                    String vatRegDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "VatRegisteredDocumentName");
                    String absoluteFilePathVatRegDoc = FOLDER_PATH + vatRegDoc;
                    BasePage.uploadFile(companyInformationPage.vatRegisteredDocument, absoluteFilePathVatRegDoc);
                } catch (ElementNotInteractableException | NoSuchElementException el) {
                    logger.info("????????? Change Vat registration document link is not available ?????????");
                }
            } else {
                BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredNo);
            }
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredNo);
        }
    }

    private void enterVatExemption(String ymlFile, String subHeader) {
        logger.info("Entering VAT exemption information");
        String vatExempt = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AreYouVatExempt");
        assert vatExempt != null;
        if (vatExempt.equalsIgnoreCase("yes")) {
            BasePage.waitUntilElementClickable(companyInformationPage.vatExemptYes, 30);
            BasePage.clickWithJavaScript(companyInformationPage.vatExemptYes);

            try {
                String vatExemptDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "VatExemptDocumentName");
                String absoluteFilePathVatExemptDoc = FOLDER_PATH + vatExemptDoc;
                BasePage.genericWait(100);
                BasePage.uploadFile(companyInformationPage.vatExemptDocument, absoluteFilePathVatExemptDoc);
            } catch (ElementNotInteractableException e) {
                logger.info("????????? Change Vat exempt document link is not available ?????????");
            }
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.vatExemptNo);
        }
    }

    private void enterAnnualCompanyTurnover(String ymlFile, String subHeader) {
        logger.info("Entering annual company turnover");
        String annualCompanyTurnOver = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AnnualCompanyTurnOverIs");
        assert annualCompanyTurnOver != null;
        if (annualCompanyTurnOver.equalsIgnoreCase("Under 10.2M")) {
            BasePage.waitUntilElementClickable(companyInformationPage.annualCompanyTurnOverUnderTen, 30);
            BasePage.clickWithJavaScript(companyInformationPage.annualCompanyTurnOverUnderTen);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.annualCompanyTurnOverOverTen);
        }

        logger.info("Entering company balance sheet");
        String companyBalanceSheet = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CompanyBalanceSheetTotalIs");
        assert companyBalanceSheet != null;
        if (companyBalanceSheet.equalsIgnoreCase("Under 5.2M")) {
            BasePage.clickWithJavaScript(companyInformationPage.balanceSheetTotalUnderFive);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.balanceSheetTotalOverFive);
        }
    }

    private void enterAverageNumberOfEmployees(String ymlFile, String subHeader) {
        logger.info("Entering number of employees");
        String averageNumEmp = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AverageNumberOfEmployees");
        if (averageNumEmp.equalsIgnoreCase("Under 50")) {
            BasePage.clickWithJavaScript(companyInformationPage.numberOfEmployeeUnderFifty);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.numberOfEmployeeOverFifty);
        }
    }

    //verify if company information is saved
    private void isCompanyInfoSaved() {
        List<WebElement> allElements = BasePage.findListOfWebElements(ProviderCompanyInformationPage.COMPANY_INFORMATION_SUB_XPATHS);

        //filter the elements that have an 'id' attribute
        List<WebElement> elementsWithIdAttribute = allElements.stream()
                .filter(element -> element.getDomAttribute("id") != null && !Objects.requireNonNull(element.getDomAttribute("id")).isEmpty())
                .toList();

        //check if any of the elements have an 'id' attribute equal to 'Icon_material-done'
        boolean hasIdDone = elementsWithIdAttribute.stream()
                .anyMatch(element -> Objects.equals(element.getDomAttribute("id"), "Icon_material-done"));

        assertThat("Company information is not saved",hasIdDone, is(true));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(companyInformationPage.successMessage, 120);
        String actualInLowerCase = BasePage.getText(companyInformationPage.successMessage).toLowerCase().trim();
        String expected = "Data saved Successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Company information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(companyInformationPage.successMessage, 20);
    }

    public void createProviderInDraftStage() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Company Information to create a provider is draft stage >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the current increment value for the provider (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);
        enterDataForInputFields(EDIT_YML_FILE, ADD);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(companyInformationPage.saveButton);
        verifySuccessMessage();
        BasePage.waitUntilElementClickable(companyInformationPage.updateButton, 120);
        isCompanyInfoSaved();

        // After successfully entering the company information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);
        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("provider_incrementValue", incrementValue);
    }

    public void updatePaymentProfile() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Editing Provider - Payment Profile >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        clickOnUpdateProfileLink();
        enterDataForInputFields(EDIT_YML_FILE, UPDATE);
        BasePage.clickWithJavaScript(companyInformationPage.saveButton);
        verifyUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(companyInformationPage.updateButton, 120);
    }

    private void clickOnUpdateProfileLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on the update profile link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(companyInformationPage.topThreeDots, 30);
        BasePage.mouseHoverAndClick(companyInformationPage.topThreeDots, companyInformationPage.updateProfileLink,
                companyInformationPage.updateProfileLinkChildElement);
        BasePage.waitUntilElementClickable(companyInformationPage.saveButton, 30);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(companyInformationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(companyInformationPage.successMessage).toLowerCase().trim();
        String expected = "Profile updated Successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Company information update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(companyInformationPage.successMessage, 20);
    }

    public void completeProviderCreationSteps() {
        enterCompanyInformation();
        siteManagementActions.addSiteManagementData();
        workerStaffActions.addingWorkerStaffData();
        workerStaffActions.clickingOnAddButton();
        workerStaffActions.moveToUserManagementPage();
        userManagementActions.addUser();
        billingInformationActions.savingGeneralBillingInformation();
        subContractingAgreementActions.clickOnCompleteProfileButton();
    }
}
