package com.carehires.actions.agency;

import com.carehires.pages.agency.SearchAgencyPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.support.PageFactory;

public class SearchAgencyActions {

    SearchAgencyPage searchAgencyPage;

    public SearchAgencyActions() {
        searchAgencyPage = new SearchAgencyPage();
        PageFactory.initElements(BasePage.getDriver(), searchAgencyPage);
    }

    public void searchByText() {
        BasePage.waitUntilPageCompletelyLoaded();
        //wait until profile status get updated
        BasePage.genericWait(10000);
        String agencyName = DataConfigurationReader.readDataFromYmlFile("agency-create", "BasicInfo", "BusinessName");
        BasePage.typeWithStringBuilder(searchAgencyPage.searchByText, agencyName);
        BasePage.clickOnEnterKey(searchAgencyPage.searchByText);
        BasePage.genericWait(10000);
    }
}
