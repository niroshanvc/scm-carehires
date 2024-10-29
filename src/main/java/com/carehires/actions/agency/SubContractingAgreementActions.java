package com.carehires.actions.agency;

import com.carehires.pages.agency.SubContractingAgreementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SubContractingAgreementActions {

    private final SubContractingAgreementPage subContractingAgreementPage;

    private static final Logger logger = LogManager.getFormatterLogger(SubContractingAgreementActions.class);

    public SubContractingAgreementActions() {
        subContractingAgreementPage = new SubContractingAgreementPage();
        PageFactory.initElements(BasePage.getDriver(), subContractingAgreementPage);
    }

    public void clickOnInviteButton() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Invite button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(subContractingAgreementPage.inviteButton);
        BasePage.waitUntilElementClickable(subContractingAgreementPage.noButton, 60);
        BasePage.clickWithJavaScript(subContractingAgreementPage.noButton);
    }

    public void clickOnCompleteProfileButton() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Complete Profile button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(subContractingAgreementPage.completeProfileButton);
        BasePage.waitUntilElementPresent(subContractingAgreementPage.attachSubContractDocumentButton, 60);

        String note = DataConfigurationReader.readDataFromYmlFile("agency","agency-create", "SubContract", "Note");
        BasePage.typeWithStringBuilder(subContractingAgreementPage.noteTextarea, note);

        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Agency" + File.separator
                + "resources" + File.separator + "Upload" + File.separator + "Agency" + File.separator + "subContractDocument.pdf";
        BasePage.uploadFile(subContractingAgreementPage.uploadFile, absoluteFilePath);
        waitUntilDocumentUploaded();
        BasePage.clickWithJavaScript(subContractingAgreementPage.saveButton);

        verifySuccessMessage();
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(subContractingAgreementPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(subContractingAgreementPage.successMessage).toLowerCase().trim();
        String expected = "Contract signed successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Contract signed success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(subContractingAgreementPage.successMessage, 20);
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementDisplayed(subContractingAgreementPage.previewSubContractDocument, 60);
        BasePage.mouseHoverOverElement(subContractingAgreementPage.previewSubContractDocument);
        BasePage.waitUntilElementDisplayed(subContractingAgreementPage.removeAttachment, 60);
    }
}
