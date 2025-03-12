package com.carehires.actions.agreements;

import com.carehires.pages.agreements.SearchAgreementsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SearchAgreementsActions {

    SearchAgreementsPage searchAgreementsPage;

    private static final String ENTITY = "agreement";
    private static final String YML_FILE_CREATE = "agreement-create";
    private static final String YML_HEADER_CREATE = "Agreement Overview";

    private static final Logger logger = LogManager.getLogger(SearchAgreementsActions.class);

    public SearchAgreementsActions() {
        searchAgreementsPage = new SearchAgreementsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), searchAgreementsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Agreement Search Page elements: {}", e.getMessage());
        }
    }

    public void searchAnAgreementUsingAdvanceFilters() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Search an agreement >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(searchAgreementsPage.advanceFiltersLink);
        BasePage.waitUntilElementPresent(searchAgreementsPage.filterByProviderDropdown, 60);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByProviderDropdown);
        String provider = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Care Provider");
        By by = By.xpath(searchAgreementsPage.getDropdownOptionXpath(provider));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(searchAgreementsPage.getDropdownOptionXpath(provider));
        BasePage.clickWithJavaScript(searchAgreementsPage.getDropdownOptionXpath(provider));

        BasePage.genericWait(500);
        String careHome = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Site");
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByCarehomeDropdown);
        by = By.xpath(searchAgreementsPage.getDropdownOptionXpath(careHome));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(searchAgreementsPage.getDropdownOptionXpath(careHome));
        BasePage.clickWithJavaScript(searchAgreementsPage.getDropdownOptionXpath(careHome));

        String agency = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Agency");
        BasePage.genericWait(2000);
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByAgencyDropdown);
        by = By.xpath(searchAgreementsPage.getDropdownOptionXpath(agency));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(searchAgreementsPage.getDropdownOptionXpath(agency));
        BasePage.clickWithJavaScript(searchAgreementsPage.getDropdownOptionXpath(agency));

        String agencyLocation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Agency Location");
        BasePage.genericWait(2000);
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByAgencyLocationDropdown);
        by = By.xpath(searchAgreementsPage.getDropdownOptionXpath(agencyLocation));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(searchAgreementsPage.getDropdownOptionXpath(agencyLocation));
        BasePage.clickWithJavaScript(searchAgreementsPage.getDropdownOptionXpath(agencyLocation));
        BasePage.genericWait(2000);
        BasePage.clickWithJavaScript(searchAgreementsPage.applyButton);

        BasePage.waitUntilElementDisappeared(searchAgreementsPage.applyButton, 60);

        //verifying wanted agreement loaded
        BasePage.genericWait(5000);
        String text = BasePage.getText(searchAgreementsPage.firstSearchedResult);
        String actual = text.split("VS")[0].trim();
        assertThat("Not displaying the correct agreement!", actual, is(agency));

        BasePage.clickWithJavaScript(searchAgreementsPage.firstSearchedResult);
    }
}
