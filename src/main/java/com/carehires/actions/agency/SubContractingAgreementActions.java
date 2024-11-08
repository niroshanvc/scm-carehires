package com.carehires.actions.agency;

import com.carehires.pages.agency.SubContractingAgreementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.FileDownloadUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

public class SubContractingAgreementActions {

    private final SubContractingAgreementPage subContractingAgreementPage;
    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Sub Contract";
    private static final String RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "resources";

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
        completeProfile(YML_FILE);
    }

    private void completeProfile(String fileName) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Complete Profile button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(subContractingAgreementPage.completeProfileButton);
        BasePage.waitUntilElementPresent(subContractingAgreementPage.attachSubContractDocumentButton, 60);

        String note = DataConfigurationReader.readDataFromYmlFile(ENTITY,fileName, YML_HEADER, "Note");
        BasePage.typeWithStringBuilder(subContractingAgreementPage.noteTextarea, note);

        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "SubContractDocument");
        String absoluteFilePath = RESOURCE_FOLDER + File.separator + "Upload" + File.separator + "Agency" + File.separator + doc;
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

    public void moveToAgreementAndCompleteTheProfile() {
        navigateToSubContractingPage();
        completeProfile(EDIT_YML_FILE);
    }

    private void navigateToSubContractingPage() {
        navigationMenu.gotoAgreementPage();
        BasePage.genericWait(3000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Sub Contracting Agreement >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(subContractingAgreementPage.inviteButton, 30);
        BasePage.genericWait(2000);
    }

    public void downloadAndDeleteAgreement() {
        // Trigger the download
        FileDownloadUtils.triggerDownloadAndCloseTab(subContractingAgreementPage.downloadAgreement);

        // Get the downloaded file name
        BasePage.genericWait(5000);
        String downloadedFileName = FileDownloadUtils.getLatestDownloadedFileName();
        if (downloadedFileName == null) {
            logger.error("Download failed! No file detected.");
        }
        // Ensure the fileName is not null or empty
        assertThat("File name should not be null", downloadedFileName, is(notNullValue()));
        assertThat("File name should not be empty", downloadedFileName, not(isEmptyOrNullString()));

        // Delete the after verification
        boolean isDeleted = FileDownloadUtils.deleteLatestDownloadedFile();
        assertThat("Downloaded file is not deleted", isDeleted, is(true));
    }
}
