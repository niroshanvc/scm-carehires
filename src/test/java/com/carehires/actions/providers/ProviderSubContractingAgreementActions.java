package com.carehires.actions.providers;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProviderSubContractingAgreementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProviderSubContractingAgreementActions {

    ProviderSubContractingAgreementPage subContractingAgreementPage;

    private static final Logger logger = LogManager.getFormatterLogger(ProviderSubContractingAgreementActions.class);

    public ProviderSubContractingAgreementActions() {
        subContractingAgreementPage = new ProviderSubContractingAgreementPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), subContractingAgreementPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickOnCompleteProfileButton() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Complete Profile button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(4000);
        BasePage.waitUntilElementClickable(subContractingAgreementPage.completeProfileButton, 60);
        BasePage.clickWithJavaScript(subContractingAgreementPage.completeProfileButton);
        BasePage.waitUntilElementPresent(subContractingAgreementPage.attachSubContractDocumentButton, 60);

        String note = DataConfigurationReader.readDataFromYmlFile("provider","provider-create", "SubContract", "Note");
        BasePage.typeWithStringBuilder(subContractingAgreementPage.noteTextarea, note);

        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Provider" + File.separator
                + "subContractDocument.pdf";
        BasePage.uploadFile(subContractingAgreementPage.uploadFile, absoluteFilePath);
        waitUntilDocumentUploaded();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(subContractingAgreementPage.saveButton);

        verifySuccessMessage();
        getProviderId();
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(subContractingAgreementPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(subContractingAgreementPage.successMessage).toLowerCase().trim();
        String expected = "Contract Signed successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Contract agreement success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(subContractingAgreementPage.successMessage, 20);
    }

    // get auto generated provider id and save it on the memory
    private void getProviderId() {
        BasePage.clickWithJavaScript(subContractingAgreementPage.companyInformationRightSideMenu);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(subContractingAgreementPage.providerId, 30);
        String headerText = BasePage.getText(subContractingAgreementPage.providerId).trim();
        String providerId = headerText.split("\n")[0];
        GlobalVariables.setVariable("providerId", providerId);
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementDisplayed(subContractingAgreementPage.previewSubContractDocument, 60);
        BasePage.mouseHoverOverElement(subContractingAgreementPage.previewSubContractDocument);
        BasePage.waitUntilElementDisplayed(subContractingAgreementPage.removeAttachment, 60);
    }
}
