package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerEmploymentHistoryPage {

    @FindBy(xpath = "//h3[text()='Worker History']/../..//button[contains(text(), 'Add new')]")
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

    @FindBy(xpath = "//div[contains(@class, 'compliance-issue-detected')]//p[1]/span")
    public WebElement careSectorRelatedExperience;

    @FindBy(xpath = "//div[contains(@class, 'compliance-issue-detected')]//p[2]/span")
    public WebElement totalExperience;

    @FindBy(xpath = "//h3[text()='Reference']/../..//button[contains(text(), 'Add new')]")
    public WebElement referenceAddNewButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='referenceType']/button")
    public WebElement referenceTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='organizationName']/button")
    public WebElement selectWorkplaceDropdown;

    @FindBy(xpath = "//textarea[@formcontrolname='referenceNote']")
    public WebElement referenceNote;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement uploadFile;

    @FindBy(xpath = "//button[contains(text(), 'Add Reference')]")
    public WebElement addReferenceButton;

    @FindBy(xpath = "(//div[contains(@class, 'common-document-table-body')])[2]/div[3]/span")
    public WebElement referenceTypeCheckingStatus;

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

    @FindBy(xpath = "//nb-dialog-container/*")
    public WebElement successPopup;

    @FindBy(xpath = "//nb-dialog-container/*//nb-card-body/h3")
    public WebElement successPopupTitle;

    @FindBy(xpath = "//nb-dialog-container/*//nb-card-footer/a")
    public WebElement successPopupNoLink;

    @FindBy(xpath = "//div[not(contains(@class, 'provider'))]//p/strong")
    public WebElement workerId;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Delete Details'])[1]")
    public WebElement deleteIcon1;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Delete Details'])[2]")
    public WebElement deleteIcon2;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Edit Details'])[1]")
    public WebElement workerHistoryEditIcon1;

    @FindBy(xpath = "(//nb-icon[@nbtooltip='Edit Details'])[2]")
    public WebElement workerHistoryEditIcon2;

    @FindBy(xpath = "//button[contains(text(),'Update Work History')]")
    public WebElement updateWorkHistoryButton;

    @FindBy(xpath = "//button[contains(text(), 'Add new')]")
    public WebElement referenceAddNewButtonInEdit;

    @FindBy(xpath = "(//div[contains(@class, 'worker-reference-header')]/div/div[2])[1]")
    public WebElement workerHistoryExpandOrClose;

    @FindBy(xpath = "(//b[text()= 'Reference Type']/ancestor::div[@class='ch-table-new']//div[contains(@class, 'common-document')])[2]//nb-icon[@nbtooltip='Delete Details']")
    public WebElement referenceDeleteIcon1;
}