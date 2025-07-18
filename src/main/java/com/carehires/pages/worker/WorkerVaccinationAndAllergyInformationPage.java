package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerVaccinationAndAllergyInformationPage {

    @FindBy(xpath = "(//button[contains(text(), 'Add New')])[1]")
    public WebElement addNewButton;

    @FindBy(xpath = "//button[contains(text(), 'Request to Complete')]")
    public WebElement requestToCompleteButton;

    @FindBy(xpath = "//input[@formcontrolname='vaccinationType']")
    public WebElement vaccinationType;

    @FindBy(id = "vaccinationDate")
    public WebElement vaccinationDate;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement selectFile;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCovidApprovedVaccine']//input")
    public WebElement covid19Checkbox;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCovidApprovedVaccine']//input/../span[1]")
    public WebElement covid19CheckboxSpan;

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