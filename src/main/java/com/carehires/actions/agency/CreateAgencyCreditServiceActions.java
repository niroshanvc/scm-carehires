package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.CreateAgencyBasicInfoPage;
import com.carehires.pages.agency.CreateAgencyCreditServicePage;
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

public class CreateAgencyCreditServiceActions {

    CreateAgencyCreditServicePage creditServicePage;
    GenericUtils genericUtils = new GenericUtils();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "CreditService";
    private static final Logger logger = LogManager.getLogger(CreateAgencyCreditServiceActions.class);

    public CreateAgencyCreditServiceActions() {
        creditServicePage = new CreateAgencyCreditServicePage();
        PageFactory.initElements(BasePage.getDriver(), creditServicePage);
    }

    public void enterCreditServiceInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Credit Service information >>>>>>>>>>>>>>>>>>>>");

        // Use the increment value retrieved in the Hooks
        int incrementValue = GlobalVariables.getVariable("incrementValue", Integer.class);

        BasePage.scrollToWebElement(creditServicePage.firstName);
        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalFirstName");
        BasePage.clearAndEnterTexts(creditServicePage.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalLastName");
        BasePage.clearAndEnterTexts(creditServicePage.lastName, lastName);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "JobTitle");
        BasePage.clearAndEnterTexts(creditServicePage.jobTitle, jobTitle);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "EmailAddress");
        BasePage.clearAndEnterTexts(creditServicePage.email, (email + incrementValue));

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Phone");
        BasePage.clearAndEnterTexts(creditServicePage.phoneNumber, phone);

        String dob = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DateOfBirth");
        BasePage.clearAndEnterTexts(creditServicePage.dateOfBirth, dob);

        String personalId = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "PersonalIdNumber");
        BasePage.clearAndEnterTexts(creditServicePage.personalIdNumber, personalId);

        String postcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "PostCode");
        genericUtils.fillAddress(creditServicePage.postcode, postcode);

        String ownership = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "OwnershipPercentage");
        BasePage.clearAndEnterTexts(creditServicePage.ownershipPercentage, ownership);

        //upload verification document
        String agencyOwnerDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "AgencyOwnerDocument");
        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + agencyOwnerDoc;
        BasePage.uploadFile(creditServicePage.agencyOwnerIdentityVerificationDoc, absoluteFilePath);
        //wait until document is uploaded
        BasePage.genericWait(4000);

        //do check agency owner checkboxes
        BasePage.scrollToWebElement(creditServicePage.addLegalRepresentativeHeader);
        BasePage.genericWait(500);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerExecutiveManagerCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerOwns25Checkbox);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerBoardMemberCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.agencyOwnerPrimaryRepresentativeCheckbox);

        //expand and enter legal rep data
        expandAddLegalRep();
        BasePage.waitUntilElementPresent(creditServicePage.legalFirstName, 10);

        BasePage.scrollToWebElement(creditServicePage.legalEmail);
        String legalRepFirstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepLegalFirstName");
        BasePage.clearAndEnterTexts(creditServicePage.legalFirstName, legalRepFirstName);

        String legalRepLastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepLegalLastName");
        BasePage.clearAndEnterTexts(creditServicePage.legalLastName, legalRepLastName);

        String legalRepJobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepJobTitle");
        BasePage.clearAndEnterTexts(creditServicePage.legalJobTitle, legalRepJobTitle);

        String legalRepEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepEmailAddress");
        BasePage.clearAndEnterTexts(creditServicePage.legalEmail, (legalRepEmail + incrementValue));

        BasePage.scrollToWebElement(creditServicePage.legalRepBoardMemberCheckbox);
        String legalRepPhone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepPhone");
        BasePage.clearAndEnterTexts(creditServicePage.legalPhoneNumber, legalRepPhone);

        String legalRepDob = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepDateOfBirth");
        BasePage.clearAndEnterTexts(creditServicePage.legalDateOfBirth, legalRepDob);

        String legalRepPersonalId = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepPersonalIdNumber");
        BasePage.clearAndEnterTexts(creditServicePage.legalPersonalIdNumber, legalRepPersonalId);

        String legalRepPostcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepPostCode");
        genericUtils.fillAddress(creditServicePage.legalPostcode, legalRepPostcode);

        String legalRepOwnership = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepOwnershipPercentage");
        BasePage.clearAndEnterTexts(creditServicePage.legalOwnershipPercentage, legalRepOwnership);

        //upload verification document
        String legalRepDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepDocument");
        String repFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + legalRepDoc;
        BasePage.uploadFile(creditServicePage.legalRepresentativeIdentityVerificationDoc, repFilePath);
        //wait until document is uploaded
        BasePage.genericWait(4000);

        //do check legal representative checkboxes
        BasePage.clickWithJavaScript(creditServicePage.legalRepExecutiveManagerCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.legalRepOwns25Checkbox);
        BasePage.clickWithJavaScript(creditServicePage.legalRepBoardMemberCheckbox);

        BasePage.clickWithJavaScript(creditServicePage.saveButton);

        isCreditServiceInfoSaved();
    }

    private void expandAddLegalRep() {
        if(BasePage.getAttributeValue(creditServicePage.addLegalRepresentativeHeader, "class").equalsIgnoreCase("collapsed")) {
            BasePage.clickWithJavaScript(creditServicePage.addLegalRepresentativeExpand);
        }
    }

    //verify if Credit Service information is saved
    private void isCreditServiceInfoSaved() {
        List<WebElement> allElements = BasePage.findListOfWebElements(CreateAgencyBasicInfoPage.BASIC_INFORMATION_SUB_XPATHS);

        //filter the elements that have an 'id' attribute
        List<WebElement> elementsWithIdAttribute = allElements.stream()
                .filter(element -> element.getAttribute("id") != null && !Objects.requireNonNull(element.getAttribute("id")).isEmpty())
                .toList();

        //check if any of the elements have an 'id' attribute equal to 'Icon_material-done'
        boolean hasIdDone = elementsWithIdAttribute.stream()
                .anyMatch(element -> Objects.equals(element.getAttribute("id"), "Icon_material-done"));

        assertThat("Basic information is not saved",hasIdDone, is(true));
    }
}
