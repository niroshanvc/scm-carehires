package com.carehires.pages.jobs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobSummaryPage {

    @FindBy(xpath = "//button[contains(text(), 'Post Job')]")
    public WebElement postJobButton;

    @FindBy(xpath = "//nb-dialog-container//nb-card-footer/a")
    public WebElement savePopupNoButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Edit')]")
    public WebElement editIcon;

    @FindBy(xpath = "//nb-dialog-container//nb-card-footer/button")
    public WebElement savePopupYesButton;

    @FindBy(xpath = "//nb-dialog-container//nb-card-footer/button")
    public String createNewTemplateButton;

    @FindBy(xpath = "//input[@id='business-name']")
    public WebElement templateNameInput;

    @FindBy(xpath = "//textarea[@id='business-name']")
    public WebElement templateDescriptionTextarea;

    @FindBy(xpath = "//nb-card-footer/button")
    public WebElement templateDetailsPopupSaveButton;

    @FindBy(xpath = "(//nb-toast//span)[2]")
    public WebElement successMessageTwo;
}
