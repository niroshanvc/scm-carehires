package com.carehires.actions.agency;

import com.carehires.pages.agency.SubContractingAgreementPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

public class SubContractingAgreementActions {

    SubContractingAgreementPage subContractingAgreementPage;

    public SubContractingAgreementActions() {
        subContractingAgreementPage = new SubContractingAgreementPage();
        PageFactory.initElements(BasePage.getDriver(), subContractingAgreementPage);
    }

    public void clickOnInviteButton() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(subContractingAgreementPage.inviteButton);
        BasePage.waitUntilElementClickable(subContractingAgreementPage.noButton, 60);
        BasePage.clickWithJavaScript(subContractingAgreementPage.noButton);
    }
}
