package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.SearchWorkerPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchWorkerActions {

    SearchWorkerPage searchPage;

    public SearchWorkerActions() {
        searchPage = new SearchWorkerPage();
        PageFactory.initElements(BasePage.getDriver(), searchPage);
    }

    public void searchByText() {
        BasePage.waitUntilPageCompletelyLoaded();

        String id = GlobalVariables.getVariable("workerId", String.class);
        BasePage.waitUntilElementDisplayed(searchPage.searchByText, 30);

        // Wait until search results are available (e.g. workerIds list is populated)
        BasePage.waitUntilElementPresent(searchPage.firstWorkerId, 30);

        int row = getWorkerFromTheList(id);

        // Check if row is valid before trying to click
        if (row >= 0 && row < searchPage.workerIds.size()) {
            BasePage.clickWithJavaScript((searchPage.workerIds).get(row));
        } else {
            throw new IllegalStateException("Worker ID not found in the list.");
        }
    }

    private int getWorkerFromTheList(String workerId) {
        List<WebElement> ids = searchPage.workerIds;

        // Check if the list is empty
        if (ids.isEmpty()) {
            throw new IllegalStateException("No workers found in the list.");
        }

        // Loop through the list to find the matching workerId
        for (int i = 0; i < ids.size(); i++) {
            if (BasePage.getText(ids.get(i)).equals(workerId)) {
                return i;
            }
        }

        // If no match found, return -1 to indicate it
        return -1;
    }
}