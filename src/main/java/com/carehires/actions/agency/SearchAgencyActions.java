package com.carehires.actions.agency;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.SearchAgencyPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchAgencyActions {

    private final SearchAgencyPage searchAgencyPage;

    public SearchAgencyActions() {
        searchAgencyPage = new SearchAgencyPage();
        PageFactory.initElements(BasePage.getDriver(), searchAgencyPage);
    }

    public void searchByText() {
        BasePage.waitUntilPageCompletelyLoaded();

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("agency_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for agency is not set in GlobalVariables.");
        }

        //wait until profile status get updated
        BasePage.waitUntilElementPresent(searchAgencyPage.searchByText, 30);
        String agencyName = DataConfigurationReader.readDataFromYmlFile("agency", "agency-create", "BasicInfo", "BusinessName");
        BasePage.typeWithStringBuilder(searchAgencyPage.searchByText, agencyName);
        BasePage.clickOnEnterKey(searchAgencyPage.searchByText);
    }

    public void findDraftAgency() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(searchAgencyPage.filterByDropdown);
        BasePage.waitUntilElementClickable(searchAgencyPage.draftAgenciesOption, 10);
        BasePage.clickWithJavaScript(searchAgencyPage.draftAgenciesOption);
    }

    public void searchRecentlyUpdatedAgency() {
        BasePage.waitUntilPageCompletelyLoaded();
        String id = GlobalVariables.getVariable("agencyId", String.class);
        BasePage.waitUntilElementDisplayed(searchAgencyPage.searchByText, 30);

        // Wait until search results are available (e.g. agency id list is populated)
        BasePage.waitUntilElementPresent(searchAgencyPage.firstAgencyId, 30);
        int row = getAgencyFromTheList(id);

        // Check if row is valid before trying to click
        if (row >= 0 && row < searchAgencyPage.agencyIds.size()) {
            BasePage.clickWithJavaScript((searchAgencyPage.agencyIds).get(row));
        } else {
            throw new IllegalStateException("Agency ID not found in the list.");
        }
    }

    private int getAgencyFromTheList(String agencyId) {
        List<WebElement> ids = searchAgencyPage.agencyIds;

        // Check if the list is empty
        if (ids.isEmpty()) {
            throw new IllegalStateException("No agencies found in the list.");
        }

        // Loop through the list to find the matching agencyId
        for (int i = 0; i < ids.size(); i++) {
            if (BasePage.getText(ids.get(i)).equals(agencyId)) {
                return i;
            }
        }

        // If no match found, return -1 to indicate it
        return -1;
    }
}
