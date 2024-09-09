package com.carehires.actions;

import com.carehires.pages.CreateAgencyBasicInfoPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.support.PageFactory;

public class CreateAgencyBasicActions {
    CreateAgencyBasicInfoPage createAgencyBasicInfoPage;

    public CreateAgencyBasicActions() {
        createAgencyBasicInfoPage = new CreateAgencyBasicInfoPage();
        PageFactory.initElements(BasePage.getDriver(), createAgencyBasicInfoPage);
    }

    public void enterBasicInfo() {
        BasePage.waitUntilPageCompletelyLoaded();
        String businessName = DataConfigurationReader.readDataFromYmlFile("agency-create", "BasicInfo", "BusinessName");
        BasePage.clearAndEnterTexts(createAgencyBasicInfoPage.businessName, businessName);
    }
}
