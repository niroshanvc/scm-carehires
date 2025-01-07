package com.carehires.actions.agreements;

import com.carehires.pages.agreements.ViewAgreementOverviewPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ViewAgreementOverviewActions {

    ViewAgreementOverviewPage viewAgreementOverviewPage;

    private static final Logger logger = LogManager.getLogger(ViewAgreementOverviewActions.class);


    public ViewAgreementOverviewActions() {
        viewAgreementOverviewPage = new ViewAgreementOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), viewAgreementOverviewPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Agreement Overview Page elements: {}", e.getMessage());
        }
    }
    public void verifyAgreementPaymentStatusAndSignatureStatus() {
        verifyAgreementPaymentStatus();
        verifyAgreementSignatureStatus();
    }

    private void verifyAgreementSignatureStatus() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying signature status >>>>>>>>>>>>>>>>>>>>");
        String actual = BasePage.getText(viewAgreementOverviewPage.signatureStatus).trim().toUpperCase();
        String expected = "PENDING TO SIGN";
        assertThat("Agreement signature status is not correct!", actual, is(expected));
    }

    private void verifyAgreementPaymentStatus() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying payment status >>>>>>>>>>>>>>>>>>>>");
        String actual = BasePage.getText(viewAgreementOverviewPage.paymentAuthorizationStatus).trim().toUpperCase();
        String expected = "PENDING PAYMENT AUTHORISATION";
        assertThat("Agreement payment status is not correct!", actual, is(expected));
    }

    public void clickOnMarkAsSigned() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Mark as Signed button >>>>>>>>>>>>>>>>>>>>");
        BasePage.scrollToWebElement(viewAgreementOverviewPage.markAsSignedButton);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.markAsSignedButton);
    }
}
