package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubContractingAgreementPage {

    @FindBy(xpath = "//button[contains(text(), 'Invite')]")
    public WebElement inviteButton;

    @FindBy(xpath = "//button[contains(text(), 'Complete Profile')]")
    public WebElement completeProfileButton;

    @FindBy(xpath = "//a[contains(text(), 'No')]")
    public WebElement noButton;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement  uploadFile;

    @FindBy(xpath = "//button[contains(text(),'Attach Sub Contract Document')]")
    public WebElement attachSubContractDocumentButton;

    @FindBy(xpath = "//textarea")
    public WebElement noteTextarea;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class, 'proof-preview')]")
    public WebElement previewSubContractDocument;

    @FindBy(xpath = "//button[contains(@class, 'remove-button')]")
    public WebElement removeAttachment;

    @FindBy(xpath = "//img[contains(@src, 'assets') and not(@class) and not(@alt)]/parent::div/a")
    public WebElement downloadAgreement;

    public static final String AGREEMENT_DOWNLOAD_LINK_TEXT = "Download Manually Signed Agreement";
}
