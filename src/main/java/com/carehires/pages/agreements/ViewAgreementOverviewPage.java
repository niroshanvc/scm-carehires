package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewAgreementOverviewPage {

    @FindBy(xpath = "//div[contains(@class, 'pending-authorization')]//div[contains(@class, 'col')]")
    public WebElement paymentAuthorizationStatus;

    @FindBy(xpath = "//div[contains(@class, 'resubmitted')]//div[contains(@class, 'col')]")
    public WebElement signatureStatus;

    @FindBy(xpath = "//button[contains(text(), 'Mark As Signed')]")
    public WebElement markAsSignedButton;
}
