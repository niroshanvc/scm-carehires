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

    private static final String YML_FILE = "agreement-edit";


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
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByProviderDropdown);
        String provider = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Care Provider");
        By by = By.xpath(getDropdownOptionXpath(provider));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(provider));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(provider));

        BasePage.genericWait(500);
        String careHome = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Site");
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByCarehomeDropdown);
        by = By.xpath(getDropdownOptionXpath(careHome));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(careHome));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(careHome));

        String agency = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Agency");
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByAgencyDropdown);
        by = By.xpath(getDropdownOptionXpath(agency));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(agency));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(agency));

        String agencyLocation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE, YML_HEADER_CREATE, "Agency Location");
        BasePage.clickWithJavaScript(searchAgreementsPage.filterByAgencyLocationDropdown);
        by = By.xpath(getDropdownOptionXpath(agencyLocation));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(agencyLocation));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(agencyLocation));

        BasePage.clickWithJavaScript(searchAgreementsPage.applyButton);

        BasePage.waitUntilElementDisappeared(searchAgreementsPage.applyButton, 60);

        //verifying wanted agreement loaded
        String actual = BasePage.getText(searchAgreementsPage.firstSearchedResult);
        assertThat("Not displaying the correct agreement!", actual, is(agency));

        BasePage.clickWithJavaScript(searchAgreementsPage.firstSearchedResult);
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
