package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateWorkerDocumentsAndProofPage {

    @FindBy(xpath = "//p[contains(text(), 'CV')]/../../div[7]//input[@type='file']")
    public WebElement cvSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'CV')]/../../div[6]/nb-icon")
    public WebElement cvFileStatus;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[7]//input[@type='file']")
    public WebElement dbsCertificateSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[2]//input")
    public WebElement dbsCertificateExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'DBS Certificate')]/../../div[6]/nb-icon")
    public WebElement dbsCertificateStatus;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[7]//input[@type='file']")
    public WebElement passportDocumentFile;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[2]//input")
    public WebElement passportDocumentExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'Passport Document')]/../../div[6]/nb-icon")
    public WebElement passportDocumentStatus;

    @FindBy(xpath = "//p[contains(text(), 'National Insurance Document')]/../../div[7]//input[@type='file']")
    public WebElement nationalInsuranceSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'National Insurance Document')]/../../div[6]/nb-icon")
    public WebElement nationalInsuranceStatus;

    @FindBy(xpath = "//p[contains(text(), 'Proof Of Address Document')]/../../div[7]//input[@type='file']")
    public WebElement proofOfAddressSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Proof Of Address Document')]/../../div[6]/nb-icon")
    public WebElement proofOfAddressStatus;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[7]//input[@type='file']")
    public WebElement drivingLicenceSelectFile;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[2]//input")
    public WebElement drivingLicenceExpiryDate;

    @FindBy(xpath = "//p[contains(text(), 'Driving Licence')]/../../div[6]/nb-icon")
    public WebElement drivingLicenceStatus;

    public static final String DRIVING_LICENCE_FIELD_XPATH = "//p[contains(text(), 'Driving Licence')]";

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
