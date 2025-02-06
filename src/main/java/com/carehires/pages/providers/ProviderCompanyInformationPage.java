package com.carehires.pages.providers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderCompanyInformationPage {

    @FindBy(xpath = "//span[contains(@class, 'image-edit')]")
    public WebElement uploadLogo;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement fileInputButton;

    @FindBy(xpath = "(//button[contains(@class, 'float-end')])[2]")
    public WebElement imageSaveButton;

    @FindBy(id="business-name")
    public WebElement companyName;

    @FindBy(xpath = "//input[@formcontrolname='businessRegistrationNumber']")
    public WebElement businessRegistrationNumber;

    @FindBy(xpath = "//nb-select[@formcontrolname='companyType']")
    public WebElement companyTypeDropdown;

    @FindBy(xpath = "//input[@formcontrolname='website']")
    public WebElement website;

    @FindBy(xpath = "//input[@formcontrolname='postcode']")
    public WebElement postcode;

    @FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "//nb-radio-group[@formcontrolname='isVatRegistered']//input[@value='true']")
    public WebElement vatRegisteredYes;

    @FindBy(xpath = "//nb-radio-group[@formcontrolname='isVatRegistered']//input[@value='false']")
    public WebElement vatRegisteredNo;

    @FindBy(id = "vat-no")
    public WebElement vatNumber;

    @FindBy(xpath = "//input[@name='undefined' and @value='true']//ancestor::nb-radio-group//ancestor::div[@class='row'][1]//div[3]//div[contains(@class, 'table')]//input[@type='file']")
    public WebElement vatRegisteredDocument;

    @FindBy(xpath = "//nb-radio-group[@formcontrolname='isVatExcempt']//input[@value='true']")
    public WebElement vatExemptYes;

    @FindBy(xpath = "//nb-radio-group[@formcontrolname='isVatExcempt']//input[@value='false']")
    public WebElement vatExemptNo;

    @FindBy(xpath = "//input[@name='isVatExcempt' and @value='true']//ancestor::nb-radio-group//ancestor::" +
            "div[@class='row'][1]/div[2]//div[contains(@class, 'table')]//input[@type='file']")
    public WebElement vatExemptDocument;

    @FindBy(xpath = "//input[@name='isAnnualCompanyTurnOverAboveLimit' and @value='true']")
    public WebElement annualCompanyTurnOverOverTen;

    @FindBy(xpath = "//input[@name='isAnnualCompanyTurnOverAboveLimit' and @value='false']")
    public WebElement annualCompanyTurnOverUnderTen;

    @FindBy(xpath = "//input[@name='isCompanyBalanceSheetTotalAboveLimit' and @value='true']")
    public WebElement balanceSheetTotalOverFive;

    @FindBy(xpath = "//input[@name='isCompanyBalanceSheetTotalAboveLimit' and @value='false']")
    public WebElement balanceSheetTotalUnderFive;

    @FindBy(xpath = "//input[@name='isAverageNumberOfEmployeeAboveLimit' and @value='true']")
    public WebElement numberOfEmployeeOverFifty;

    @FindBy(xpath = "//input[@name='isAverageNumberOfEmployeeAboveLimit' and @value='false']")
    public WebElement numberOfEmployeeUnderFifty;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    public static final String COMPANY_INFORMATION_SUB_XPATHS = "//h6[text()='Company Information']/..//nb-icon/*/*" +
            "/*[contains(@data-name, 'done')]";

    @FindBy(xpath = "//div[contains(@class, 'profileStatus')]//div[contains(@class, 'text')]")
    public WebElement profileStatus;

    @FindBy(xpath = "//button[contains(text(), 'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//p[contains(@class, 'text-icon') and contains(text(), '…')]")
    public WebElement topThreeDots;

    @FindBy(xpath = "//div[contains(@class, 'side')]/ul[@class='expanded-nav']/li[1]")
    public WebElement updateProfileLink;

    public By updateProfileLinkChildElement = By.xpath("//div[contains(@class, 'side')]/ul[@class='expanded-nav']/li[1]");

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
