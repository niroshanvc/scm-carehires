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

    @FindBy(xpath = "(//span[contains(text(), 'I accept all the terms')])[1]")
    public WebElement providerTermsAndConditionsText;

    @FindBy(xpath = "(//span[contains(text(), 'I accept all the terms')])[2]")
    public WebElement agencyTermsAndConditionsText;

    @FindBy(xpath = "//div[@class='job-details-section']//textarea")
    public WebElement attachAgreementNote;

    @FindBy(xpath = "(//nb-checkbox[@status='basic'])[1]")
    public WebElement providerTermsAndConditionsCheckbox;

    @FindBy(xpath = "(//nb-checkbox[@status='basic'])[2]")
    public WebElement agencyTermsAndConditionsCheckbox;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement  uploadFile;

    @FindBy(xpath = "//div[contains(@class, 'proof-preview')]")
    public WebElement previewSubContractDocument;

    @FindBy(xpath = "//button[contains(@class, 'remove-button')]")
    public WebElement removeAttachment;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Activate Agreement')]")
    public WebElement activateAgreementButton;
}
