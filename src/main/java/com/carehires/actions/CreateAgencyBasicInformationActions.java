package com.carehires.actions;

import com.carehires.pages.CreateAgencyBasicInfoPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.openqa.selenium.support.PageFactory;

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

        String providerLogo = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "CompanyLogo");
        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Logo\\" + providerLogo;
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.uploadLogo);
        BasePage.uploadFile(createAgencyBasicInfoPage.fileInputButton, absoluteFilePath);
        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.imageSaveButton);

        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "AlsoKnownAs");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.alsoKnownAs, alsoKnownAs);

        String postcode = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "PostCode");
        genericUtils.fillAddress(createAgencyBasicInfoPage.postcode, postcode);
        genericUtils.fillPhoneNumber(createAgencyBasicInfoPage.phoneNumberInput);

        BasePage.clickWithJavaScript(createAgencyBasicInfoPage.saveButton);
        BasePage.waitUntilElementClickable(createAgencyBasicInfoPage.skipButton, 30);
    }
}
