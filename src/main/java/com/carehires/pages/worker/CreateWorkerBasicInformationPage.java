package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateWorkerBasicInformationPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='agency']/button")
    public WebElement agencyDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='agencyLocations']/button")
    public WebElement agencyLocationDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='nationality']/button")
    public WebElement nationalityDropdown;

    @FindBy(xpath = "//span[contains(@class, 'image-edit')]")
    public WebElement uploadLogo;

    @FindBy(xpath = "(//input[@type='file'])[2]")
    public WebElement uploadLogoInput;

    @FindBy(xpath = "(//button[contains(@class, 'float-end')])[2]")
    public WebElement imageSaveButton;

    @FindBy(xpath = "//input[@formcontrolname='firstName']")
    public WebElement firstName;

    @FindBy(xpath = "//input[@formcontrolname='lastName']")
    public WebElement lastName;

    @FindBy(xpath = "//input[@formcontrolname='otherName']")
    public WebElement alsoKnownAs;

    @FindBy(xpath = "//input[@formcontrolname='email']")
    public WebElement email;

    @FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "//input[@formcontrolname='dob']")
    public WebElement dateOfBirth;

    @FindBy(xpath = "//input[@name='gender']")
    public List<WebElement> genderRadioButton;

    @FindBy(xpath = "//p[contains(text(),'Residential Address Information ')]/ancestor::nb-accordion-item")
    public WebElement residentialAddressInformationHeader;

    @FindBy(xpath = "//p[contains(text(),'Residential Address Information ')]/following-sibling::nb-icon")
    public WebElement residentialAddressInformationHeaderExpandIcon;

    @FindBy(xpath = "//nb-select[@formcontrolname='country']/button")
    public WebElement countryDropdown;

    @FindBy(xpath = "//input[@formcontrolname='postcode']")
    public WebElement postcode;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCurrentlyResiding']//input")
    public WebElement isCurrentlyLivingCheckbox;

    @FindBy(xpath = "//input[@formcontrolname='livingfrom']")
    public WebElement livingFrom;

    @FindBy(xpath = "//input[@formcontrolname='livingto']")
    public WebElement livingTo;

    @FindBy(id = "hubspot-deals-reference-living-to")
    public WebElement durationInAddress;

    @FindBy(xpath = "(//input[@type='file'])[1]")
    public WebElement proofOfAddressDocument;

    @FindBy(xpath = "//p[contains(text(),'Employment Information')]/ancestor::nb-accordion-item")
    public WebElement employmentInformationHeader;

    @FindBy(xpath = "//p[contains(text(),'Employment Information')]/following-sibling::nb-icon")
    public WebElement employmentInformationHeaderExpandIcon;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement workerSkillsDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='regulatorySettingTypes']/button")
    public WebElement regulatorySettingsDropdown;

    @FindBy(xpath = "//input[@formcontrolname='payrollReferenceNumber']")
    public WebElement payrollReferenceNumber;

    @FindBy(xpath = "//input[@name='employeeType']")
    public List<WebElement> employeementTypeRadioButton;

    @FindBy(xpath = "//p[contains(text(),'Passport, Visa')]/ancestor::nb-accordion-item")
    public WebElement passportInformationHeader;

    @FindBy(xpath = "//p[contains(text(),'Passport, Visa')]/following-sibling::nb-icon")
    public WebElement passportInformationHeaderExpandIcon;

    @FindBy(xpath = "//input[@formcontrolname='nationalInsuranceNumber']")
    public WebElement nationalInsuranceNumber;

    @FindBy(xpath = "//input[@formcontrolname='dbsNumber']")
    public WebElement dbsCertificateNumber;

    @FindBy(xpath = "//input[@name='hasConviction']")
    public List<WebElement> hasConvictionRadioButton;

    @FindBy(xpath = "//p[contains(text(),'Travel &')]/ancestor::nb-accordion-item")
    public WebElement travelInformationHeader;

    @FindBy(xpath = "//p[contains(text(),'Travel &')]/following-sibling::nb-icon")
    public WebElement travelInformationHeaderExpandIcon;

    @FindBy(xpath = "//input[@formcontrolname='travelDistance']")
    public WebElement travelDistance;

    @FindBy(xpath = "//input[@name='hasDrivingLicense']")
    public List<WebElement> hasDrivingLicenseRadioButton;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//p[contains(text(), 'Document is added')]")
    public WebElement documentUploadedSuccessMessage;

    public static final String AGENCY_LOCATION_CHECKBOXES = "//div[@id='cdk-overlay-1']//nb-option/nb-checkbox";
}
