package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateBasicInformationPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='agency']/button")
    public WebElement agencyDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='agencyLocations']/button")
    public WebElement agencyLocationDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='nationality']/button")
    public WebElement nationalityDropdown;

    @FindBy(xpath = "//span[contains(@class, 'image-edit')]")
    public WebElement uploadLogo;

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
    public WebElement genderRadioButton;

    @FindBy(xpath = "//p[contains(text(), 'Residential Address Information')]/..")
    public WebElement residentialAddressInformationHeader;

    @FindBy(xpath = "//nb-select[@formcontrolname='country']/button")
    public WebElement countryDropdown;

    @FindBy(xpath = "//input[@formcontrolname='postcode']")
    public WebElement postcode;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCurrentlyResiding']//input")
    public WebElement isCurrentlyResidingCheckbox;

    @FindBy(xpath = "//input[@formcontrolname='livingfrom']")
    public WebElement livingFrom;

    @FindBy(xpath = "//label[contains(text(), 'Duration')]/../input")
    public WebElement durationInAddress;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement proofOfAddressDocument;

    @FindBy(xpath = "//p[contains(text(), 'Employment Information')]/..")
    public WebElement employmentInformationHeader;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement workerSkillsDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='regulatorySettingTypes']/button")
    public WebElement regulatorySettingsDropdown;

    @FindBy(xpath = "//input[@formcontrolname='payrollReferenceNumber']")
    public WebElement payrollReferenceNumber;

    @FindBy(xpath = "//input[@name='employeeType']")
    public WebElement employeeTypeRadioButton;

    @FindBy(xpath = "//p[contains(text(), 'Passport')]/..")
    public WebElement passportInformationHeader;

    @FindBy(xpath = "//input[@formcontrolname='nationalInsuranceNumber']")
    public WebElement nationalInsuranceNumber;

    @FindBy(xpath = "//input[@formcontrolname='dbsNumber']")
    public WebElement dbsCertificateNumber;

    @FindBy(xpath = "//input[@name='hasConviction']")
    public WebElement hasConvictionRadioButton;

    @FindBy(xpath = "//p[contains(text(), 'Travel')]/..")
    public WebElement travelInformationHeader;

    @FindBy(xpath = "//input[@formcontrolname='travelDistance']")
    public WebElement travelDistance;

    @FindBy(xpath = "//input[@name='hasDrivingLicense']")
    public WebElement hasDrivingLicenseRadioButton;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;
}
