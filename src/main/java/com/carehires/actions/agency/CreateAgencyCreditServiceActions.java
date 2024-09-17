package com.carehires.actions.agency;

import com.carehires.pages.agency.CreateAgencyCreditServicePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.openqa.selenium.support.PageFactory;

public class CreateAgencyCreditServiceActions {

    CreateAgencyCreditServicePage creditServicePage;
    GenericUtils genericUtils = new GenericUtils();

    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "CreditService";


    public CreateAgencyCreditServiceActions() {
        creditServicePage = new CreateAgencyCreditServicePage();
        PageFactory.initElements(BasePage.getDriver(), creditServicePage);
    }

    public void enterCreditServiceInformation() {
        BasePage.scrollToWebElement(creditServicePage.firstName);
        String firstName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalFirstName");
        BasePage.clearAndEnterTexts(creditServicePage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalLastName");
        BasePage.clearAndEnterTexts(creditServicePage.lastName, lastName);

        String maidenName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "MaidenName");
        BasePage.clearAndEnterTexts(creditServicePage.maidenName, maidenName);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "JobTitle");
        BasePage.clearAndEnterTexts(creditServicePage.jobTitle, jobTitle);

        String email = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "EmailAddress");
        BasePage.clearAndEnterTexts(creditServicePage.email, email);

        String phone = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "Phone");
        BasePage.clearAndEnterTexts(creditServicePage.phoneNumber, phone);

        String dob = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "DateOfBirth");
        BasePage.clearAndEnterTexts(creditServicePage.dateOfBirth, dob);

        String personalId = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PersonalIdNumber");
        BasePage.clearAndEnterTexts(creditServicePage.personalIdNumber, personalId);

        String postcode = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PostCode");
        genericUtils.fillAddress(creditServicePage.postcode, postcode);

        BasePage.scrollToWebElement(creditServicePage.addLegalRepresentativeHeader);
        String ownership = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "OwnershipPercentage");
        BasePage.clearAndEnterTexts(creditServicePage.ownershipPercentage, ownership);

        //upload verification document
        String agencyOwnerDoc = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "AgencyOwnerDocument");
        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + agencyOwnerDoc;
        BasePage.typeWithStringBuilder(creditServicePage.uploadAgencyOwner, absoluteFilePath);

        //do check agency owner checkboxes
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerExecutiveManagerCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerOwns25Checkbox);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerBoardMemberCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerPrimaryRepresentativeCheckbox);

        //expand and enter legal rep data
        expandAddLegalRep();
        BasePage.waitUntilElementPresent(creditServicePage.legalFirstName, 10);

        BasePage.scrollToWebElement(creditServicePage.legalMaidenName);
        String legalRepFirstName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepLegalFirstName");
        BasePage.clearAndEnterTexts(creditServicePage.legalFirstName, legalRepFirstName);

        String legalRepLastName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepLegalLastName");
        BasePage.clearAndEnterTexts(creditServicePage.legalLastName, legalRepLastName);

        String legalRepMaidenName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepMaidenName");
        BasePage.clearAndEnterTexts(creditServicePage.legalMaidenName, legalRepMaidenName);

        String legalRepJobTitle = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepJobTitle");
        BasePage.clearAndEnterTexts(creditServicePage.legalJobTitle, legalRepJobTitle);

        String legalRepEmail = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepEmailAddress");
        BasePage.clearAndEnterTexts(creditServicePage.legalEmail, legalRepEmail);

        String legalRepPhone = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepPhone");
        BasePage.clearAndEnterTexts(creditServicePage.legalPhoneNumber, legalRepPhone);

        String legalRepDob = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepDateOfBirth");
        BasePage.clearAndEnterTexts(creditServicePage.legalDateOfBirth, legalRepDob);

        String legalRepPersonalId = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepPersonalIdNumber");
        BasePage.clearAndEnterTexts(creditServicePage.legalPersonalIdNumber, legalRepPersonalId);

        String legalRepPostcode = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepPostCode");
//        genericUtils.fillAddress(creditServicePage.legalPostcode, legalRepPostcode);

        String legalRepOwnership = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepOwnershipPercentage");
        BasePage.clearAndEnterTexts(creditServicePage.legalOwnershipPercentage, legalRepOwnership);

        //upload verification document
        String legalRepDoc = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "LegalRepDocument");
        String repFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + legalRepDoc;
        BasePage.typeWithStringBuilder(creditServicePage.uploadLegalRepresentative, repFilePath);

        //do check legal representative checkboxes
        BasePage.clickWithJavaScript(creditServicePage.legalRepExecutiveManagerCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.legalRepOwns25Checkbox);
        BasePage.clickWithJavaScript(creditServicePage.legalRepBoardMemberCheckbox);

        BasePage.clickWithJavaScript(creditServicePage.saveButton);
    }

    private void expandAddLegalRep() {
        if(BasePage.getAttributeValue(creditServicePage.addLegalRepresentativeHeader, "class").equalsIgnoreCase("collapsed")) {
            BasePage.clickWithJavaScript(creditServicePage.addLegalRepresentativeExpand);
        }
    }
}
