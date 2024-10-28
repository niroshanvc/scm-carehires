package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.CreateAgencyBasicInfoPage;
import com.carehires.pages.agency.CreateAgencyCreditServicePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateAgencyCreditServiceActions {

    private final CreateAgencyCreditServicePage creditServicePage;
    private static final GenericUtils genericUtils = new GenericUtils();

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

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        if (BasePage.isElementEnabled(creditServicePage.firstName) && BasePage.isElementDisplayed(creditServicePage.firstName)) {
            String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalFirstName");
            //Add a short wait to ensure the element is ready for interaction after the page or any JavaScript has fully executed.
            BasePage.genericWait(1000);
            BasePage.waitUntilElementClickable(creditServicePage.firstName, 60);
            BasePage.scrollToWebElement(creditServicePage.firstName);
            BasePage.clearAndEnterTexts(creditServicePage.firstName, firstName);
        } else {
            throw new InvalidElementStateException("First name field is not available");
        }

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalLastName");
        BasePage.clearAndEnterTexts(creditServicePage.lastName, lastName);

        String jobTitle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "JobTitle");
        BasePage.clearAndEnterTexts(creditServicePage.jobTitle, jobTitle);

        String email = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "EmailAddress");
        BasePage.clearAndEnterTexts(creditServicePage.email, email);

        String phone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Phone");
        BasePage.clearAndEnterTexts(creditServicePage.phoneNumber, phone);

        String dob = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DateOfBirth");
        BasePage.clickWithJavaScript(creditServicePage.dateOfBirth);
        genericUtils.selectDateFromCalendarPopup(dob);

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
        waitUntilDocumentUploaded();

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
        BasePage.clearAndEnterTexts(creditServicePage.legalEmail, legalRepEmail );

        BasePage.scrollToWebElement(creditServicePage.legalRepBoardMemberCheckbox);
        String legalRepPhone = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepPhone");
        BasePage.clearAndEnterTexts(creditServicePage.legalPhoneNumber, legalRepPhone);

        String legalRepDob = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "LegalRepDateOfBirth");
        BasePage.clickWithJavaScript(creditServicePage.legalDateOfBirth);
        genericUtils.selectDateFromCalendarPopup(legalRepDob);

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
        waitUntilDocumentUploaded();

        //do check legal representative checkboxes
        BasePage.clickWithJavaScript(creditServicePage.legalRepExecutiveManagerCheckbox);
        BasePage.clickWithJavaScript(creditServicePage.legalRepOwns25Checkbox);
        BasePage.clickWithJavaScript(creditServicePage.legalRepBoardMemberCheckbox);

        BasePage.genericWait(2000);
        BasePage.clickWithJavaScript(creditServicePage.saveButton);

        verifySuccessMessage();
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

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(creditServicePage.successMessage, 30);
        BasePage.genericWait(2000);
        String actualInLowerCase = BasePage.getText(creditServicePage.successMessage).toLowerCase().trim();
        String expected = "Business information updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Credit information saved success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(creditServicePage.successMessage, 20);
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementPresent(creditServicePage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(creditServicePage.successMessage).toLowerCase().trim();
        String expected = "Document uploaded successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Document uploaded success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(creditServicePage.successMessage, 20);
    }
}
