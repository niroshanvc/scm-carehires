package com.carehires.pages.worker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WorkerBasicInformationPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='agency']/button")
    public WebElement agencyDropdown;

    @FindBy(id = "agency-input")
    public WebElement agencyInput;

    @FindBy(xpath = "//nb-select[@formcontrolname='agencyLocations']/button")
    public WebElement agencyLocationDropdown;

    @FindBy(xpath = "//input[@formcontrolname='nationality']")
    public WebElement nationalityInput;

    @FindBy(xpath = "//span[contains(@class, 'image-edit')]")
    public WebElement uploadLogo;

    @FindBy(xpath = "(//input[@type='file'])[2]")
    public WebElement uploadLogoInput;

    @FindBy(xpath = "(//button[contains(@class, 'float-end')])[2]")
    public WebElement imageSaveButton;

    @FindBy(xpath = "//input[@formcontrolname='firstName']")
    public WebElement firstName;

    @FindBy(xpath = "//div[@class='basic-info-section']//input[@formcontrolname='firstName']")
    public WebElement firstNameUpdateField;

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

    @FindBy(xpath = "//input[@formcontrolname='address']")
    public WebElement residentialAddressInput;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCurrentlyResiding']//input")
    public WebElement isCurrentlyLivingCheckbox;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='isCurrentlyResiding']//input/../span[1]")
    public WebElement isCurrentlyLivingCheckboxChecked;

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

    @FindBy(xpath = "//nb-select[@formcontrolname='workerTypes']//button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement workerSkillsDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='regulatorySettingTypes']/button")
    public WebElement regulatorySettingsDropdown;

    @FindBy(xpath = "//input[@name='employeeType' and @value='paye']")
    public WebElement payeEmploymentTypeRadio;

    @FindBy(xpath = "//input[@formcontrolname='payrollReferenceNumber']")
    public WebElement payrollReferenceNumberInput;

    @FindBy(xpath = "//input[@name='employeeType' and @value='umbrellaCompany']")
    public WebElement umbrellaEmploymentTypeRadio;

    @FindBy(xpath = "//input[@formcontrolname='umbrellaCompanyName']")
    public WebElement umbrellaCompanyNameInput;

    @FindBy(xpath = "//input[@name='employeeType' and @value='other']")
    public WebElement otherEmploymentTypeRadio;

    @FindBy(xpath = "//p[contains(text(),'Passport, Visa')]/ancestor::nb-accordion-item")
    public WebElement passportInformationHeader;

    @FindBy(xpath = "//p[contains(text(),'Passport, Visa')]/following-sibling::nb-icon")
    public WebElement passportInformationHeaderExpandIcon;

    @FindBy(xpath = "//input[@formcontrolname='passportNumber']")
    public WebElement passportNumber;

    @FindBy(xpath = "//nb-select[@formcontrolname='issuedCountry']/button")
    public WebElement issuedCountryDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='visaType']/button")
    public WebElement visaTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='sponsorType']/button")
    public WebElement sponsorTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='assignedProvider']/button")
    public WebElement assignedProviderDropdown;

    @FindBy(xpath = "//input[@formcontrolname='nationalInsuranceNumber']")
    public WebElement nationalInsuranceNumber;

    @FindBy(xpath = "//input[@formcontrolname='visaQuota']")
    public WebElement maximumWeeklyHours;

    @FindBy(xpath = "//input[@formcontrolname='visaQuota']")
    public WebElement minimumWeeklyHours;

    @FindBy(xpath = "//input[@formcontrolname='cosIssueCompany']")
    public WebElement companyNameCosDocument;

    @FindBy(xpath = "//input[@formcontrolname='shareCode']")
    public WebElement shareCode;

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

    @FindBy(xpath = "//p[contains(@class, 'text-icon') and contains(text(), '…')]")
    public WebElement topThreeDots;

    @FindBy(xpath = "//div[contains(@class, 'side')]/ul[@class='expanded-nav']/li[1]")
    public WebElement updateProfileLink;

    public static final By updateProfileLinkChildElement = By.xpath("//li/span[contains(text(), " +
            "'Update Profile')]");

    @FindBy(xpath = "//div[not(contains(@class, 'provider'))]//p//img/..")
    public WebElement workerId;

    @FindBy(xpath = "(//div[contains(@id,'cdk-overlay')]//nb-option)[1]")
    public WebElement agencyLocation;

    @FindBy(xpath = "//nb-option[contains(@class,'multiple') and (contains(@class, 'selected'))]")
    public List<WebElement> alreadySelectedOptions;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    public String getWorkerTypeDropdownOptionXpath(String option) {
        return String.format("//nb-option[text()='%s ']", option);
    }

    public static final By threeDotsMenuLocator = By.cssSelector("p.text-icon");

    public static final By updateProfileLinkLocator = By.xpath(
            "//span[@class='menu-text' and text()='Update Profile']");
}
