package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
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

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for provider is not set in GlobalVariables.");
        }

        //wait until profile status get updated
        BasePage.waitUntilElementPresent(searchAgencyPage.searchByText, 30);
        String agencyName = DataConfigurationReader.readDataFromYmlFile("agency", "agency-create", "BasicInfo", "BusinessName");
        BasePage.typeWithStringBuilder(searchAgencyPage.searchByText, agencyName);
        BasePage.clickOnEnterKey(searchAgencyPage.searchByText);
        getAgencyId();
    }

    // get auto generated provider id and save it on the memory
    private void getAgencyId() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(searchAgencyPage.agencyId, 30);
        String headerText = BasePage.getText(searchAgencyPage.agencyId).trim();
        String agencyId = headerText.split("\n")[0];
        GlobalVariables.setVariable("agencyId", agencyId);
    }
}
