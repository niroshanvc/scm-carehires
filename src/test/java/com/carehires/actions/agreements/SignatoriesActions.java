package com.carehires.actions.agreements;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.agreements.SignatoriesPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SignatoriesActions {

    SignatoriesPage signatoriesPage;
    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_FILE_SMOKE = "agreement-create-smoke";
    private static final String SKILLS = "With Skills";
    private static final String YML_HEADER = "Signatories";
    private static final String YML_HEADER_AGENCY = "Agency";
    private static final String YML_HEADER_PROVIDER = "Provider";
    Integer incrementValue;

    private static final Logger logger = LogManager.getLogger(SignatoriesActions.class);

    public SignatoriesActions() {
        signatoriesPage = new SignatoriesPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), signatoriesPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Signatories Page elements: {}", e.getMessage());
        }
    }

    public void addSignatoryInfo() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Signatories Info >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("agreement_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for agreement is not set in GlobalVariables.");
        }

        String agencyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS,
                YML_HEADER, YML_HEADER_AGENCY, "Name");
        BasePage.clearAndEnterTexts(signatoriesPage.agencyName, agencyName);

        String agencyDesignation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS,
                YML_HEADER, YML_HEADER_AGENCY, "Designation");
        BasePage.clearAndEnterTexts(signatoriesPage.agencyDesignation, agencyDesignation);

        String agencyEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS,
                YML_HEADER, YML_HEADER_AGENCY, "Email");
        BasePage.clearAndEnterTexts(signatoriesPage.agencyEmail, agencyEmail);

        String providerName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS,
                YML_HEADER, YML_HEADER_PROVIDER, "Name");
        BasePage.clearAndEnterTexts(signatoriesPage.providerName, providerName);

        String providerDesignation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS,
                YML_HEADER, YML_HEADER_PROVIDER, "Designation");
        BasePage.clearAndEnterTexts(signatoriesPage.providerDesignation, providerDesignation);

        String providerEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS,
                YML_HEADER, YML_HEADER_PROVIDER, "Email");
        BasePage.clearAndEnterTexts(signatoriesPage.providerEmail, providerEmail);

        BasePage.clickWithJavaScript(signatoriesPage.agreementCheckbox);
        BasePage.clickTabKey(signatoriesPage.agreementCheckbox);
        BasePage.clickWithJavaScript(signatoriesPage.finaliseAndCheckButton);
        confirmAgreementCreation();
        verifyWorkerRateIsAddedSuccessfully();
    }

    private void confirmAgreementCreation() {
        BasePage.waitUntilElementPresent(signatoriesPage.authoriseAndCreateButton, 60);
        BasePage.clickWithJavaScript(signatoriesPage.authoriseAndCreateButton);
    }

    private void verifyWorkerRateIsAddedSuccessfully() {
        BasePage.waitUntilElementPresent(signatoriesPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(signatoriesPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker rate is not created!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(signatoriesPage.successMessage, 20);
    }

    public void addSignatoryInfoForAgreement() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Signatories Info >>>>>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("agreement_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for agreement is not set in GlobalVariables.");
        }

        String agencyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS,
                YML_HEADER, YML_HEADER_AGENCY, "Name");
        BasePage.clearAndEnterTexts(signatoriesPage.agencyName, agencyName);

        String agencyDesignation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE,
                SKILLS, YML_HEADER, YML_HEADER_AGENCY, "Designation");
        BasePage.clearAndEnterTexts(signatoriesPage.agencyDesignation, agencyDesignation);

        String agencyEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS,
                YML_HEADER, YML_HEADER_AGENCY, "Email");
        BasePage.clearAndEnterTexts(signatoriesPage.agencyEmail, agencyEmail);

        String providerName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS,
                YML_HEADER, YML_HEADER_PROVIDER, "Name");
        BasePage.clearAndEnterTexts(signatoriesPage.providerName, providerName);

        String providerDesignation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE,
                SKILLS, YML_HEADER, YML_HEADER_PROVIDER, "Designation");
        BasePage.clearAndEnterTexts(signatoriesPage.providerDesignation, providerDesignation);

        String providerEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS,
                YML_HEADER, YML_HEADER_PROVIDER, "Email");
        BasePage.clearAndEnterTexts(signatoriesPage.providerEmail, providerEmail);

        BasePage.clickWithJavaScript(signatoriesPage.agreementCheckbox);
        BasePage.clickTabKey(signatoriesPage.agreementCheckbox);
        BasePage.clickWithJavaScript(signatoriesPage.finaliseAndCheckButton);
        confirmAgreementCreation();
        verifyWorkerRateIsAddedSuccessfully();
    }
}
