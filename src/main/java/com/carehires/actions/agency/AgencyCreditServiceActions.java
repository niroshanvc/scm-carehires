package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.CreateAgencyBasicInfoPage;
import com.carehires.pages.agency.AgencyCreditServicePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgencyCreditServiceActions {

    private final AgencyCreditServicePage creditServicePage;
    private static final GenericUtils genericUtils = new GenericUtils();
    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Credit Service";
    private static final String EDIT_YML_HEADER = "Credit Service";
    private static final String EDIT_YML_SUB_HEADER = "Agency Owner Information";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String CHECKED_VALUE = "custom-checkbox checked";
    private static final String USER_DIR = "user.dir";
    private static final String RESOURCE = "resources";
    private static final String UPLOAD = "Upload";
    private static final String AGENCY = "Agency";
    private static final Logger logger = LogManager.getLogger(AgencyCreditServiceActions.class);

    public AgencyCreditServiceActions() {
        creditServicePage = new AgencyCreditServicePage();
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
        String absoluteFilePath = System.getProperty(USER_DIR) + File.separator  + "src" + File.separator + "test"
                + File.separator + RESOURCE + File.separator + UPLOAD + File.separator + AGENCY + File.separator
                + agencyOwnerDoc;
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
        String repFilePath = System.getProperty(USER_DIR) +  File.separator + "src" + File.separator + "test"
                + File.separator + RESOURCE + File.separator + UPLOAD + File.separator + AGENCY + File.separator
                + legalRepDoc;
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
        if(BasePage.getAttributeValue(creditServicePage.addLegalRepresentativeHeader, CLASS_ATTRIBUTE).equalsIgnoreCase("collapsed")) {
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

    public void editCreditService() {
        navigateToCreditServicePage();
        updateBasicDetails();
        uploadIdentityVerificationDocument();
        updateExecutiveInfo();
        BasePage.clickWithJavaScript(creditServicePage.saveButton);
        verifySuccessMessage();
    }

    private void navigateToCreditServicePage() {
        navigationMenu.gotoCreditServicePage();
        BasePage.genericWait(3000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating Credit Service information >>>>>>>>>>>>>>>>>>>>");
        if (BasePage.isElementDisplayed(creditServicePage.updateButton)) {
            BasePage.clickWithJavaScript(creditServicePage.updateButton);
        }
        BasePage.waitUntilElementPresent(creditServicePage.saveButton, 30);
        BasePage.genericWait(2000);
    }

    private void updateBasicDetails() {
        enterText(creditServicePage.firstName, "FirstName");
        enterText(creditServicePage.lastName, "LastName");
        enterText(creditServicePage.jobTitle, "JobTitle");
        enterText(creditServicePage.email, "EmailAddress");
        enterText(creditServicePage.phoneNumber, "Phone");

        BasePage.clickWithJavaScript(creditServicePage.dateOfBirth);
        genericUtils.selectDateFromCalendarPopup(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, EDIT_YML_HEADER, EDIT_YML_SUB_HEADER, "DateOfBirth"));

        enterText(creditServicePage.personalIdNumber, "NINumber");
        genericUtils.fillAddress(creditServicePage.postcode, DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, EDIT_YML_HEADER, EDIT_YML_SUB_HEADER, "PostCode"));
        BasePage.clickWithJavaScript(creditServicePage.ownershipPercentage);
        enterText(creditServicePage.ownershipPercentage, "OwnershipPercentage");
    }

    private void enterText(WebElement field, String key) {
        String value = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, EDIT_YML_HEADER, EDIT_YML_SUB_HEADER, key);
        BasePage.clearAndEnterTexts(field, value);
    }

    private void uploadIdentityVerificationDocument() {
        if (BasePage.isElementDisplayed(creditServicePage.deleteUploadedFile)) {
            BasePage.clickWithJavaScript(creditServicePage.deleteUploadedFile);
            waitUntilDocumentRemoved();
        }

        String identityVerificationDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, EDIT_YML_HEADER, EDIT_YML_SUB_HEADER, "IdentityVerificationDocument");
        String absoluteFilePath = System.getProperty(USER_DIR) + File.separator + "src" + File.separator + "test" + File.separator + RESOURCE + File.separator + UPLOAD + File.separator + AGENCY + File.separator + identityVerificationDoc;
        BasePage.uploadFile(creditServicePage.agencyOwnerIdentityVerificationDoc, absoluteFilePath);
        waitUntilDocumentUploaded();
    }

    private void updateExecutiveInfo() {
        toggleCheckbox(creditServicePage.agencyOwnerExecutiveManagerCheckbox, creditServicePage.agencyOwnerExecutiveManagerCheckboxSpan, "This person is an executive");
        toggleCheckbox(creditServicePage.agencyOwnerOwns25Checkbox, creditServicePage.agencyOwnerOwns25CheckboxSpan, "This person owns 25%");
        toggleCheckbox(creditServicePage.agencyOwnerBoardMemberCheckbox, creditServicePage.agencyOwnerBoardMemberCheckboxSpan, "This person is a member");
        toggleCheckbox(creditServicePage.agencyOwnerPrimaryRepresentativeCheckbox, creditServicePage.agencyOwnerPrimaryRepresentativeCheckboxSpan, "This person is authorised");
        toggleCheckbox(creditServicePage.isAlsoTheLegalRepresentative, creditServicePage.isAlsoTheLegalRepresentativeSpan, "This person is the legal representative");
    }

    private void toggleCheckbox(WebElement checkbox, WebElement checkboxSpan, String key) {
        String isChecked = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, EDIT_YML_HEADER, EDIT_YML_SUB_HEADER, key);
        if (isChecked.equalsIgnoreCase("Yes")) {
            String value = BasePage.getAttributeValue(checkboxSpan, CLASS_ATTRIBUTE);
            if (!value.equalsIgnoreCase(CHECKED_VALUE)) {
                BasePage.clickWithJavaScript(checkbox);
            }
        }
    }

    private void waitUntilDocumentRemoved() {
        BasePage.waitUntilElementPresent(creditServicePage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(creditServicePage.successMessage).toLowerCase().trim();
        String expected = "File deleted successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Document deleted success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(creditServicePage.successMessage, 20);
    }
}
