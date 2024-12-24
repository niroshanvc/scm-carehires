package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerEducationAndTrainingPage {

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

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Delete'])[1]")
    public WebElement deleteIcon1;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Delete'])[2]")
    public WebElement deleteIcon2;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement deleteButton;

    @FindBy(xpath = "(//nb-checkbox[contains(@nbtooltip, 'Care Academy issued')])[1]//input")
    public WebElement caIssuedCheckbox1;
}
