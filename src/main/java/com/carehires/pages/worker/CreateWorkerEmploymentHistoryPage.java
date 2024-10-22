package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateWorkerEmploymentHistoryPage {

    @FindBy(xpath = "(//button[contains(@class, 'inserted') and (contains(@class, 'button'))])[1]")
    public WebElement workerHistoryAddNewButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='employmentType']/button")
    public WebElement employmentTypeDropdown;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCurrentlyWorking']//input")
    public WebElement isCurrentlyWorkingHere;

    @FindBy(xpath = "//input[@formcontrolname='organizationName']")
    public WebElement companyName;

    @FindBy(xpath = "//input[@formcontrolname='designationTitle']")
    public WebElement designation;

    @FindBy(xpath = "//input[@formcontrolname='from']")
    public WebElement from;

    @FindBy(xpath = "//input[@formcontrolname='to']")
    public WebElement to;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCareRelatedWorkExperience']//input")
    public WebElement isCareRelatedWorkExperience;

    @FindBy(xpath = "//nb-select[@formcontrolname='reason']/button")
    public WebElement reasonForLeavingDropdown;

    @FindBy(xpath = "//textarea[@formcontrolname='additionalInfo']")
    public WebElement additionalNote;

    @FindBy(xpath = "//button[contains(text(), 'Add Work History')]")
    public WebElement addWorkHistoryButton;

    @FindBy(xpath = "(//button[contains(@class, 'inserted') and (contains(@class, 'button'))])[2]")
    public WebElement referenceAddNewButton;

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
}