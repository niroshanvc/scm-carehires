package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderSubContractingAgreementPage {

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

    @FindBy(xpath = "//div[not(contains(@class, 'provider'))]//p/strong")
    public WebElement providerId;

    @FindBy(xpath = "//img[contains(@src, 'profile_step_info')]")
    public WebElement companyInformationRightSideMenu;

    @FindBy(xpath = "//div[contains(@class, 'proof-preview')]")
    public WebElement previewSubContractDocument;

    @FindBy(xpath = "//button[contains(@class, 'remove-button')]")
    public WebElement removeAttachment;
}
