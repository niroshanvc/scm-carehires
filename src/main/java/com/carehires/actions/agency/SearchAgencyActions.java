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
        String agencyName = DataConfigurationReader.readDataFromYmlFile("agency-create", "SubContract", "BusinessName");
        BasePage.typeWithStringBuilder(searchAgencyPage.searchByText, agencyName);
        BasePage.clickOnEnterKey(searchAgencyPage.searchByText);
    }
}
