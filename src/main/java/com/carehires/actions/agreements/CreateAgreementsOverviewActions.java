package com.carehires.actions.agreements;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.agreements.CreateAgreementsOverviewPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateAgreementsOverviewActions {

    CreateAgreementsOverviewPage agreementsOverviewPage;

    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_HEADER = "Agreement Overview";

    private static final Logger logger = LogManager.getLogger(CreateAgreementsOverviewActions.class);

    public CreateAgreementsOverviewActions() {
        agreementsOverviewPage = new CreateAgreementsOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), agreementsOverviewPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Create Agreement Overview Page elements: {}", e.getMessage());
        }
    }

    public void enterOverviewInfo() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Agreement Overview Info >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the current increment value for the provider (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);

        // Retrieve the latest agency and provider increment values
        int agencyIncrementValue = DataConfigurationReader.getCurrentIncrementValue("agency");
        int providerIncrementValue = DataConfigurationReader.getCurrentIncrementValue("provider");

        // Read agency name from YAML and replace <agencyIncrement> placeholder
        String agencyTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Agency");
        assert agencyTemplate != null;
        String agency = agencyTemplate.replace("<agencyIncrement>", String.valueOf(agencyIncrementValue));

        BasePage.waitUntilElementClickable(agreementsOverviewPage.agencyDropdown, 60);
        BasePage.genericWait(2000);
        BasePage.clickWithJavaScript(agreementsOverviewPage.agencyDropdown);
        By by = By.xpath(agreementsOverviewPage.getDropdownOptionXpath(agency));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(agreementsOverviewPage.getDropdownOptionXpath(agency));
        BasePage.clickWithJavaScript(agreementsOverviewPage.getDropdownOptionXpath(agency));
        BasePage.genericWait(2000);

        String agencyLocation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                "Agency Location");
        BasePage.clickWithJavaScript(agreementsOverviewPage.agencyLocationDropdown);
        BasePage.waitUntilElementClickable(agreementsOverviewPage.getDropdownOptionXpath(agencyLocation), 30);
        By agencyLocationBy = By.xpath(agreementsOverviewPage.getDropdownOptionXpath(agencyLocation));
        BasePage.waitUntilVisibilityOfElementLocated(agencyLocationBy, 20);
        BasePage.clickWithJavaScript(agreementsOverviewPage.getDropdownOptionXpath(agencyLocation));

        // Read provider name from YAML and replace <providerIncrement> placeholder
        String careProviderTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Care Provider");
        assert careProviderTemplate != null;
        String careProvider = careProviderTemplate.replace("<providerIncrement>", String.valueOf(providerIncrementValue));
        BasePage.clickWithJavaScript(agreementsOverviewPage.careProviderDropdown);
        By careProviderBy = By.xpath(agreementsOverviewPage.getDropdownOptionXpath(careProvider));
        BasePage.waitUntilVisibilityOfElementLocated(careProviderBy, 20);
        BasePage.scrollToWebElement(agreementsOverviewPage.getDropdownOptionXpath(careProvider));
        BasePage.clickWithJavaScript(agreementsOverviewPage.getDropdownOptionXpath(careProvider));
        BasePage.genericWait(2000);

        String siteTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Site");
        assert siteTemplate != null;
        String site = siteTemplate.replace("<providerIncrement>", String.valueOf(providerIncrementValue));
        BasePage.clickWithJavaScript(agreementsOverviewPage.siteDropdown);
        BasePage.waitUntilElementClickable(agreementsOverviewPage.getDropdownOptionXpath(site), 30);
        selectAllSites();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(agreementsOverviewPage.continueButton);
        verifySuccessMessage();

        // After successfully entering the company information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);

        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("agreement_incrementValue", incrementValue);
    }

    // method to select all the options available in the Site multi select dropdown
    private void selectAllSites() {
        List<WebElement> checkboxes = BasePage.findListOfWebElements(CreateAgreementsOverviewPage.SITE_CHECKBOXES);
        if (!BasePage.getAttributeValue(agreementsOverviewPage.site, "class").contains("selected")) {
            for (WebElement checkbox : checkboxes) {
                BasePage.clickWithJavaScript(checkbox);
            }
        }
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(agreementsOverviewPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(agreementsOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Agreement created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Agreement is not created!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agreementsOverviewPage.successMessage, 20);
    }
}
