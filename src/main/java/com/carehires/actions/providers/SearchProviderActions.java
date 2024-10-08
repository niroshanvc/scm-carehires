package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.SearchProviderPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
        BasePage.typeWithStringBuilder(searchPage.searchByText, id);
        BasePage.clickOnEnterKey(searchPage.searchByText);
        waitUntilSearchIsOver();
    }

    private void waitUntilSearchIsOver() {
        By providerLocator = By.xpath(SearchProviderPage.PROVIDER_XPATH);

        WebDriverWait wait = new WebDriverWait(BasePage.getDriver(), Duration.ofSeconds(30));

        // Wait until either the provider list contains elements or "No matching results found" is displayed.
        wait.until(driver -> {
            List<WebElement> listOfWebElements = driver.findElements(providerLocator);

            boolean noResultsMessage = BasePage.findListOfWebElements(String.valueOf(searchPage.noResultText)).size() > 0;

            // Wait until either a valid result or the "No matching results found" message appears
            return !listOfWebElements.isEmpty() || noResultsMessage;
        });

        // Check if the "No matching results found" message is displayed
        if (BasePage.isElementDisplayed(searchPage.noResultText)) {
            throw new AssertionError("No matching results found");
        }

        // Now the list should contain 1 valid result
        List<WebElement> listOfWebElements = BasePage.findListOfWebElements(providerLocator);
        if (!listOfWebElements.isEmpty()) {
            WebElement firstElement = listOfWebElements.get(0);

            // Wait until the first result is displayed
            BasePage.waitUntilElementDisplayed(firstElement, 20);

            // Verify the actual provider name with the expected name from YAML file
            String actualText = BasePage.getText(firstElement).trim();
            String provider = DataConfigurationReader.readDataFromYmlFile("provider-create", "CompanyInformation", "CompanyName");
            assertThat("Provider name is not matching", actualText, is(provider));
        }
    }
}
