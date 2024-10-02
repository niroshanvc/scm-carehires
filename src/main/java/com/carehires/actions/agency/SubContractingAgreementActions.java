package com.carehires.actions.agency;

import com.carehires.pages.agency.SubContractingAgreementPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class SubContractingAgreementActions {

    SubContractingAgreementPage subContractingAgreementPage;

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

        String note = DataConfigurationReader.readDataFromYmlFile("agency-create", "SubContract", "Note");
        BasePage.typeWithStringBuilder(subContractingAgreementPage.noteTextarea, note);

        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Agency\\" + "subContractDocument.pdf";
        BasePage.uploadFile(subContractingAgreementPage.uploadFile, absoluteFilePath);
        BasePage.genericWait(10000);
        BasePage.clickWithJavaScript(subContractingAgreementPage.saveButton);

        //wait until profile status got updated as 'Profile Complete'
        BasePage.genericWait(10000);
    }
}
