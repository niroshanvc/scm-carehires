package com.carehires.actions.agency;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.CreateAgencyBasicInfoPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateAgencyBasicInformationActions {

    CreateAgencyBasicInfoPage createAgencyBasicInfoPage;
    private static final GenericUtils genericUtils;
    AgencyBusinessLocationsActions businessLocationsActions;
    AgencyCreditServiceActions creditServiceActions;
    AgencyStaffActions staffActions;
    BillingProfileManagementActions billingProfileManagementActions;
    AgencyUserManagementActions userManagementActions;
    SubContractingAgreementActions subContractingAgreementActions;

    static {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Basic Info";
    private static final String ADD = "Add";
    private static final Logger logger = LogManager.getLogger(CreateAgencyBasicInformationActions.class);

    public CreateAgencyBasicInformationActions()  {
        createAgencyBasicInfoPage = new CreateAgencyBasicInfoPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), createAgencyBasicInfoPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }

        // Initialize action classes
        businessLocationsActions = new AgencyBusinessLocationsActions();
        creditServiceActions = new AgencyCreditServiceActions();
        staffActions = new AgencyStaffActions();
        billingProfileManagementActions = new BillingProfileManagementActions();
        userManagementActions = new AgencyUserManagementActions();
        subContractingAgreementActions = new SubContractingAgreementActions();
    }

    public void enterBasicInfo()  {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering basic information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the current increment value for the agency
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);
        logger.info("Initial increment value retrieved for agency: {}", incrementValue);

        enterData(YML_FILE, ADD);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.saveButton);
        BasePage.waitUntilElementClickable(createAgencyBasicInfoPage.skipButton, 90);

        isBasicInfoSaved();

        // Store in GlobalVariables for use by subsequent methods
        GlobalVariables.setVariable(ENTITY + "_incrementValue", incrementValue);
        logger.info("Stored current agency_incrementValue {} in GlobalVariables", incrementValue);
    }

    private void enterData(String ymlFile, String subHeader) {
        String businessName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "BusinessName");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessName, businessName);

        String businessRegistrationNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "BusinessRegistrationNumber");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessRegistrationNumber, businessRegistrationNumber);

        //upload a logo
        String companyLogo = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "CompanyLogo");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Agency" + File.separator + companyLogo;
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.uploadLogo);
        BasePage.uploadFile(createAgencyBasicInfoPage.fileInputButton, absoluteFilePath);
        BasePage.genericWait(500);
        BasePage.waitUntilElementClickable(createAgencyBasicInfoPage.imageSaveButton, 120);
        logger.info("Save button displayed: {}", createAgencyBasicInfoPage.saveButton.isDisplayed());
        logger.info("Save button enabled: {}", createAgencyBasicInfoPage.saveButton.isEnabled());
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.imageSaveButton);

        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "AlsoKnownAs");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.alsoKnownAs, alsoKnownAs);

        //enter postcode and select a valid address
        String postcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "PostCode");
        genericUtils.fillAddress(createAgencyBasicInfoPage.postcode, postcode, 190);

        //enter phone number
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, createAgencyBasicInfoPage.phoneNumberInput, YML_HEADER, subHeader, "PhoneNumberType");
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, createAgencyBasicInfoPage.phoneNumberInput, YML_HEADER, subHeader, "PhoneNumber");
        BasePage.clickTabKey(createAgencyBasicInfoPage.phoneNumberInput);
    }

    //verify if basic information is saved
    private void isBasicInfoSaved() {
        List<WebElement> allElements = BasePage.findListOfWebElements(CreateAgencyBasicInfoPage.BASIC_INFORMATION_SUB_XPATHS);

        //filter the elements that have an 'id' attribute
        List<WebElement> elementsWithIdAttribute = allElements.stream()
                .filter(element -> element.getDomAttribute("id") != null && !Objects.requireNonNull(element.getDomAttribute("id")).isEmpty())
                .toList();

        //check if any of the elements have an 'id' attribute equal to 'Icon_material-done'
        boolean hasIdDone = elementsWithIdAttribute.stream()
                .anyMatch(element -> Objects.equals(element.getDomAttribute("id"), "Icon_material-done"));

        assertThat("Basic information is not saved", hasIdDone, is(true));
    }

    public void createAgencyInDraftStage() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Creating an agency in Draft stage >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the current increment value for the agency (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);
        enterData(EDIT_YML_FILE, ADD);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.saveButton);
        BasePage.waitUntilElementClickable(createAgencyBasicInfoPage.skipButton, 90);
        isBasicInfoSaved();

        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("agency_incrementValue", incrementValue);

    }

    public void moveToBasicInfoStage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Basic Information Stage >>>>>>>>>>>>>>>>>>>>");
        BasePage.genericWait(1500);
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.basicInformationCompletedIcon);
    }

    public void getGeneratedAgencyId() {
        getAgencyId();
    }

    // get auto generated agency id and save it on the memory
    private void getAgencyId() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Reading auto generated agency id >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(createAgencyBasicInfoPage.agencyId, 90);
        String headerText = BasePage.getText(createAgencyBasicInfoPage.agencyId).trim();
        String agencyId = headerText.split("\n")[0];
        GlobalVariables.setVariable("agencyId", agencyId);
    }

    public void completeCreateAgencyProcess() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Completing new agency creation >>>>>>>>>>>>>>>>>>>>");
        enterBasicInfo();
        creditServiceActions.enterCreditServiceInformation();
        businessLocationsActions.addLocationDetails();
        staffActions.addStaff();
        billingProfileManagementActions.addBilling();
        userManagementActions.addUser();
        subContractingAgreementActions.clickOnCompleteProfileButton();
    }
}
