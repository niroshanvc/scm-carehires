package com.carehires.actions;

import com.carehires.pages.CreateAgencyBasicInfoPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateAgencyBasicInformationActions {

    CreateAgencyBasicInfoPage createAgencyBasicInfoPage;
    GenericUtils genericUtils = new GenericUtils();

    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "BasicInfo";

    public CreateAgencyBasicInformationActions() {
        createAgencyBasicInfoPage = new CreateAgencyBasicInfoPage();
        PageFactory.initElements(BasePage.getDriver(), createAgencyBasicInfoPage);
    }

    public void enterBasicInfo() {
        BasePage.waitUntilPageCompletelyLoaded();
        String businessName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "BusinessName");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessName, businessName);

        String businessRegistrationNumber = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "BusinessRegistrationNumber");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessRegistrationNumber, businessRegistrationNumber);

        //upload a logo
        String providerLogo = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "CompanyLogo");
        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + providerLogo;
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.uploadLogo);
        BasePage.uploadFile(createAgencyBasicInfoPage.fileInputButton, absoluteFilePath);
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.imageSaveButton);

        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "AlsoKnownAs");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.alsoKnownAs, alsoKnownAs);

        //enter postcode and select a valid address
        String postcode = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PostCode");
        genericUtils.fillAddress(createAgencyBasicInfoPage.postcode, postcode);

        //enter phone number
        genericUtils.fillPhoneNumber(createAgencyBasicInfoPage.phoneNumberInput);
        BasePage.clickTabKey(createAgencyBasicInfoPage.phoneNumberInput);
        BasePage.genericWait(5000);

        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.saveButton);
        BasePage.waitUntilElementClickable(createAgencyBasicInfoPage.skipButton, 90);

        isBasicInfoSaved();
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
