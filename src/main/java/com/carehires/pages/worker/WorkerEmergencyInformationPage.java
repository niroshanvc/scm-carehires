package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerEmergencyInformationPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(xpath = "//input[@formcontrolname='name']")
    public WebElement emergencyContactName;

    @FindBy(xpath = "//input[@formcontrolname='relationship']")
    public WebElement relationship;

    @FindBy(xpath = "//input[@formcontrolname='primaryContactNumber']")
    public WebElement primaryContactNumber;

    @FindBy(xpath = "//input[@formcontrolname='secondaryContactNumber']")
    public WebElement secondaryContactNumber;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Edit Details'])[1]")
    public WebElement editIcon1;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Edit Details'])[2]")
    public WebElement editIcon2;
}
