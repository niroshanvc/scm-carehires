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
import static org.hamcrest.core.Is.is;

public class CreateAgencyBasicInformationActions {

    CreateAgencyBasicInfoPage createAgencyBasicInfoPage;
    private static final GenericUtils genericUtils = new GenericUtils();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "BasicInfo";
    private static final Logger logger = LogManager.getLogger(CreateAgencyBasicInformationActions.class);

    public CreateAgencyBasicInformationActions() {
        createAgencyBasicInfoPage = new CreateAgencyBasicInfoPage();
        PageFactory.initElements(BasePage.getDriver(), createAgencyBasicInfoPage);
    }

    public void enterBasicInfo() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering basic information >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the current increment value for the provider (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);

        String businessName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "BusinessName");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessName, businessName);

        String businessRegistrationNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "BusinessRegistrationNumber");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessRegistrationNumber, businessRegistrationNumber);

        //upload a logo
        String companyLogo = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CompanyLogo");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Agency" + File.separator + companyLogo;
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.uploadLogo);
        BasePage.uploadFile(createAgencyBasicInfoPage.fileInputButton, absoluteFilePath);
        BasePage.waitUntilElementDisplayed(createAgencyBasicInfoPage.imageSaveButton, 60);
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.imageSaveButton);

        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "AlsoKnownAs");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.alsoKnownAs, alsoKnownAs);

        //enter postcode and select a valid address
        String postcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "PostCode");
        genericUtils.fillAddress(createAgencyBasicInfoPage.postcode, postcode);

        //enter phone number
        genericUtils.fillPhoneNumber(ENTITY, YML_FILE, createAgencyBasicInfoPage.phoneNumberInput, YML_HEADER, "PhoneNumber");
        BasePage.clickTabKey(createAgencyBasicInfoPage.phoneNumberInput);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.saveButton);
        BasePage.waitUntilElementClickable(createAgencyBasicInfoPage.skipButton, 90);

        isBasicInfoSaved();

        // After successfully entering the basic information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);

        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("provider_incrementValue", incrementValue+1);
    }

    //verify if basic information is saved
    private void isBasicInfoSaved() {
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
