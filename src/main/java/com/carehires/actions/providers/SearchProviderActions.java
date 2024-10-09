package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.SearchProviderPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchProviderActions {

    SearchProviderPage searchPage;

    public SearchProviderActions() {
        searchPage = new SearchProviderPage();
        PageFactory.initElements(BasePage.getDriver(), searchPage);
    }

    public void searchByText() {
        BasePage.waitUntilPageCompletelyLoaded();

        String id = GlobalVariables.getVariable("providerId", String.class);
        BasePage.waitUntilElementDisplayed(searchPage.searchByText, 30);

        // Wait until search results are available (e.g. providerIds list is populated)
        BasePage.waitUntilElementPresent(searchPage.firstProviderId, 30);

        int row = getProviderFromTheList(id);

        // Check if row is valid before trying to click
        if (row >= 0 && row < searchPage.providerIds.size()) {
            BasePage.clickWithJavaScript((searchPage.providerIds).get(row));
        } else {
            throw new IllegalStateException("Provider ID not found in the list.");
        }
    }

    private int getProviderFromTheList(String providerId) {
        List<WebElement> ids = searchPage.providerIds;

        // Check if the list is empty
        if (ids.isEmpty()) {
            throw new IllegalStateException("No providers found in the list.");
        }

        // Loop through the list to find the matching providerId
        for (int i = 0; i < ids.size(); i++) {
            if (BasePage.getText(ids.get(i)).equals(providerId)) {
                return i;
            }
        }

        // If no match found, return -1 to indicate it
        return -1;
    }

}
