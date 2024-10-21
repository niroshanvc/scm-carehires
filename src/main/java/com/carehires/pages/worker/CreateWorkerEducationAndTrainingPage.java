package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateWorkerEducationAndTrainingPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='certificateName']/button")
    public WebElement certificateDropdown;

    @FindBy(xpath = "//input[@formcontrolname='expiryDate']")
    public WebElement validUntilInput;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement uploadCertificate;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class, 'document-table-body')]//span[contains(@class, 'success')]")
    public WebElement certificateValidityStatus;
}
