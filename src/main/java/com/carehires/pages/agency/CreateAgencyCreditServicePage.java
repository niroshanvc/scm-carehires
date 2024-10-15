package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAgencyCreditServicePage {

    @FindBy(xpath = "(//input[@formcontrolname='firstName'])[1]")
    public WebElement firstName;

    @FindBy(xpath = "(//input[@formcontrolname='lastName'])[1]")
    public WebElement lastName;

    @FindBy(xpath = "(//input[@formcontrolname='maidenName'])[1]")
    public WebElement maidenName;

    @FindBy(xpath = "(//input[@formcontrolname='designation'])[1]")
    public WebElement jobTitle;

    @FindBy(xpath = "(//input[@formcontrolname='email'])[1]")
    public WebElement email;

    @FindBy(xpath = "(//input[@formcontrolname='phoneNumber'])[1]")
    public WebElement phoneNumber;

    @FindBy(xpath = "(//input[@formcontrolname='dob'])[1]")
    public WebElement dateOfBirth;

    @FindBy(xpath = "(//input[@formcontrolname='idNumber'])[1]")
    public WebElement personalIdNumber;

    @FindBy(xpath = "(//input[@formcontrolname='postcode'])[1]")
    public WebElement postcode;

    @FindBy(xpath = "(//input[@formcontrolname='ownership'])[1]")
    public WebElement ownershipPercentage;

    @FindBy(xpath = "(//input[@formcontrolname='firstName'])[2]")
    public WebElement legalFirstName;

    @FindBy(xpath = "(//input[@formcontrolname='lastName'])[2]")
    public WebElement legalLastName;

    @FindBy(xpath = "(//input[@formcontrolname='maidenName'])[2]")
    public WebElement legalMaidenName;

    @FindBy(xpath = "(//input[@formcontrolname='designation'])[2]")
    public WebElement legalJobTitle;

    @FindBy(xpath = "(//input[@formcontrolname='email'])[2]")
    public WebElement legalEmail;

    @FindBy(xpath = "(//input[@formcontrolname='phoneNumber'])[2]")
    public WebElement legalPhoneNumber;

    @FindBy(xpath = "(//input[@formcontrolname='dob'])[2]")
    public WebElement legalDateOfBirth;

    @FindBy(xpath = "(//input[@formcontrolname='idNumber'])[2]")
    public WebElement legalPersonalIdNumber;

    @FindBy(xpath = "(//input[@formcontrolname='postcode'])[2]")
    public WebElement legalPostcode;

    @FindBy(xpath = "(//input[@formcontrolname='ownership'])[2]")
    public WebElement legalOwnershipPercentage;

    @FindBy(xpath = "//h3[contains(text(),'Legal Representative')]/ancestor::nb-accordion-item")
    public WebElement addLegalRepresentativeHeader;

    @FindBy(xpath = "//h3[contains(text(),'Legal Representative')]/ancestor::nb-accordion-item-header//nb-icon")
    public WebElement addLegalRepresentativeExpand;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "(//input[@type='file'])[1]")
    public WebElement agencyOwnerIdentityVerificationDoc;

    @FindBy(xpath = "(//input[@type='file'])[2]")
    public WebElement legalRepresentativeIdentityVerificationDoc;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='isExecutiveManager']//input)[1]")
    public WebElement agencyOwnerExecutiveManagerCheckbox;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='owns25Percent']//input)[1]")
    public WebElement agencyOwnerOwns25Checkbox;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='isBoardMember']//input)[1]")
    public WebElement agencyOwnerBoardMemberCheckbox;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='isPrimaryRepresentative']//input)[1]")
    public WebElement agencyOwnerPrimaryRepresentativeCheckbox;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='isExecutiveManager']//input)[2]")
    public WebElement legalRepExecutiveManagerCheckbox;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='owns25Percent']//input)[2]")
    public WebElement legalRepOwns25Checkbox;

    @FindBy(xpath = "(//nb-checkbox[@formcontrolname='isBoardMember']//input)[2]")
    public WebElement legalRepBoardMemberCheckbox;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
