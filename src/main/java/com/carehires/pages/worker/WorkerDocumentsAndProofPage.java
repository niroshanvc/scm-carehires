package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerDocumentsAndProofPage {

    @FindBy(xpath = "//p[contains(text(), 'CV')]/../../div[7]//input[@type='file']")
    public WebElement cvSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'CV')]/../../div[6]/nb-icon")
    public WebElement cvFileStatus;

    @FindBy(xpath = "//p[contains(text(), 'CV')]/../../div[8]/span/nb-icon")
    public WebElement cvFileSDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[7]//input[@type='file']")
    public WebElement dbsCertificateSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[2]//input")
    public WebElement dbsCertificateExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[6]/nb-icon")
    public WebElement dbsCertificateStatus;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[8]/span/nb-icon")
    public WebElement dbsCertificateDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[7]//input[@type='file']")
    public WebElement passportDocumentFile;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[2]//input")
    public WebElement passportDocumentExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[6]/nb-icon")
    public WebElement passportDocumentStatus;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[8]/span/nb-icon")
    public WebElement passportDocumentDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'National Insurance Document')]/../../div[7]//input[@type='file']")
    public WebElement nationalInsuranceSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'National Insurance Document')]/../../div[6]/nb-icon")
    public WebElement nationalInsuranceStatus;

    @FindBy(xpath = "//p[contains(text(), 'National Insurance Document')]/../../div[8]/span/nb-icon")
    public WebElement nationalInsuranceDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'Proof Of Address Document')]/../../div[7]//input[@type='file']")
    public WebElement proofOfAddressSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Proof Of Address Document')]/../../div[6]/nb-icon")
    public WebElement proofOfAddressStatus;

    @FindBy(xpath = "//p[contains(text(), 'Proof Of Address Document')]/../../div[8]/span/nb-icon")
    public WebElement proofOfAddressDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[7]//input[@type='file']")
    public WebElement drivingLicenceSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[2]//input")
    public WebElement drivingLicenceExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[6]/nb-icon")
    public WebElement drivingLicenceStatus;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[8]/span/nb-icon")
    public WebElement drivingLicenceDeleteIcon;

    public static final String DRIVING_LICENCE_FIELD_XPATH = "//p[contains(text(), 'Driving Licence')]";

    @FindBy(xpath = "//p[contains(text(), 'Visa')]/../../div[7]//input[@type='file']")
    public WebElement visaSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Visa')]/../../div[2]//input")
    public WebElement visaExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'Visa')]/../../div[6]/nb-icon")
    public WebElement visaStatus;

    @FindBy(xpath = "//p[contains(text(), 'Visa')]/../../div[8]/span/nb-icon")
    public WebElement visaDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'Covid-19')]/../../div[7]//input[@type='file']")
    public WebElement covid19SelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Covid-19')]/../../div[6]/nb-icon")
    public WebElement covid19Status;

    @FindBy(xpath = "//p[contains(text(), 'Covid-19')]/../../div[8]/span/nb-icon")
    public WebElement covid19DeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'COS Document')]/../../div[7]//input[@type='file']")
    public WebElement cosDocumentSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'COS Document')]/../../div[2]//input")
    public WebElement cosDocumentExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'COS Document')]/../../div[6]/nb-icon")
    public WebElement cosDocumentStatus;

    @FindBy(xpath = "//p[contains(text(), 'COS Document')]/../../div[8]/span/nb-icon")
    public WebElement cosDocumentDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'PVG Certificate')]/../../div[7]//input[@type='file']")
    public WebElement pvgCertificateSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'PVG Certificate')]/../../div[6]/nb-icon")
    public WebElement pvgCertificateStatus;

    @FindBy(xpath = "//p[contains(text(), 'PVG Certificate')]/../../div[8]/span/nb-icon")
    public WebElement pvgCertificateDeleteIcon;

    @FindBy(xpath = "//p[contains(text(), 'Regulatory Setting')]/../../div[7]//input[@type='file']")
    public WebElement regulatorySettingsSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Regulatory Setting')]/../../div[6]/nb-icon")
    public WebElement regulatorySettingsStatus;

    @FindBy(xpath = "//p[contains(text(), 'Regulatory Setting')]/../../div[8]/span/nb-icon")
    public WebElement regulatorySettingsDeleteIcon;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[text()= 'Update ']")
    public WebElement updateButton;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement deleteButton;
}
