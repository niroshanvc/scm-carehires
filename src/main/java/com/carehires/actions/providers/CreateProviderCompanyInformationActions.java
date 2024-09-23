package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateProviderCompanyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateProviderCompanyInformationActions {
    
    CreateProviderCompanyInformationPage companyInformationPage;
    GenericUtils genericUtils = new GenericUtils();

    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "CompanyInformation";
    
    public CreateProviderCompanyInformationActions() {
        companyInformationPage =  new CreateProviderCompanyInformationPage();
        PageFactory.initElements(BasePage.getDriver(), companyInformationPage);
    }

    public void enterCompanyInformation() {
        BasePage.waitUntilPageCompletelyLoaded();

        //upload a logo
        String providerLogo = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "ProviderLogo");
        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + providerLogo;
        BasePage.clickWithJavaScript(companyInformationPage.uploadLogo);
        BasePage.uploadFile(companyInformationPage.fileInputButton, absoluteFilePath);
        BasePage.clickWithJavaScript(companyInformationPage.imageSaveButton);

        String companyName = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "CompanyName");
        BasePage.typeWithStringBuilder(companyInformationPage.companyName, companyName);

        String businessRegNum = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "BusinessRegistrationNumber");
        BasePage.typeWithStringBuilder(companyInformationPage.businessRegistrationNumber, businessRegNum);

        String companyType = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "CompanyType");
        BasePage.clickWithJavaScript(companyInformationPage.companyTypeDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(companyType));

        String website = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "Website");
        BasePage.typeWithStringBuilder(companyInformationPage.website, website);

        //enter postcode and select a valid address
        String providerPostcode = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "ProviderPostCode");
        genericUtils.fillAddress(companyInformationPage.postcode, providerPostcode);

        //enter phone number
        genericUtils.fillPhoneNumber(YML_FILE, YML_HEADER, "PhoneNumber", companyInformationPage.phoneNumberInput);
        BasePage.clickTabKey(companyInformationPage.phoneNumberInput);

        String vatRegistered = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AreYouVatRegistered");
        if (vatRegistered.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredYes);
            BasePage.waitUntilElementPresent(companyInformationPage.vatRegisterDocument, 10);
            String doc = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "VatRegisteredDocument");
            BasePage.uploadFile(companyInformationPage.vatRegisterDocument, doc);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.vatRegisteredNo);
        }

        String vatExempt = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AreYouVatExempt");
        if (vatExempt.equalsIgnoreCase("yes")) {
            BasePage.clickWithJavaScript(companyInformationPage.vatExemptYes);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.vatExemptNo);
        }

        String annualCompanyTurnOver = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AnnualCompanyTurnOverIs");
        if (annualCompanyTurnOver.equalsIgnoreCase("Under 10.2M")) {
            BasePage.clickWithJavaScript(companyInformationPage.annualCompanyTurnOverUnderTen);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.annualCompanyTurnOverOverTen);
        }

        String comapnyBalanceSheet = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "CompanyBalanceSheetTotalIs");
        if (comapnyBalanceSheet.equalsIgnoreCase("Under 5.2M")) {
            BasePage.clickWithJavaScript(companyInformationPage.balanceSheetTotalUnderFive);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.balanceSheetTotalOverFive);
        }

        String averageNumEmp = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "AverageNumberOfEmployees");
        if (averageNumEmp.equalsIgnoreCase("Under 50")) {
            BasePage.clickWithJavaScript(companyInformationPage.numberOfEmployeeUnderFifty);
        } else {
            BasePage.clickWithJavaScript(companyInformationPage.numberOfEmployeeOverFifty);
        }
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    public void verifyProfileStatus() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(2000);
        BasePage.waitUntilElementPresent(companyInformationPage.profileStatus, 60);
        String actual = BasePage.getText(companyInformationPage.profileStatus).toLowerCase().trim();
        assertThat("Agent profile is not valid", actual, is("profile complete"));
    }
}
