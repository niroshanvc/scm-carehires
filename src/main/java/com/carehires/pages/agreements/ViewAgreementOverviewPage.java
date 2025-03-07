package com.carehires.pages.agreements;

import com.carehires.utils.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ViewAgreementOverviewPage {

    @FindBy(xpath = "//div[contains(@class, 'pending-authorization')]//div[contains(@class, 'col')]")
    public WebElement paymentAuthorizationStatus;

    @FindBy(xpath = "//div[contains(@class, 'resubmitted')]//div[contains(@class, 'col')]")
    public WebElement signatureStatus;

    @FindBy(xpath = "//div[contains(@class, 'status-div')]//div[contains(@class, 'col')]")
    public List<WebElement> signatureStatusSigned;

    @FindBy(xpath = "//button[contains(text(), 'Mark As Signed')]")
    public WebElement markAsSignedButton;

    @FindBy(xpath = "(//span[contains(text(), 'I accept all the terms')])[1]")
    public WebElement providerTermsAndConditionsText;

    @FindBy(xpath = "(//span[contains(text(), 'I accept all the terms')])[2]")
    public WebElement agencyTermsAndConditionsText;

    @FindBy(xpath = "//div[@class='job-details-section']//textarea")
    public WebElement attachAgreementNote;

    @FindBy(xpath = "(//nb-checkbox[@status='basic'])[1]//input")
    public WebElement providerTermsAndConditionsCheckbox;

    @FindBy(xpath = "(//nb-checkbox[@status='basic'])[2]//input")
    public WebElement agencyTermsAndConditionsCheckbox;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement  uploadFile;

    @FindBy(xpath = "//div[contains(@class, 'proof-preview')]")
    public WebElement previewSubContractDocument;

    @FindBy(xpath = "//button[contains(@class, 'remove-button')]")
    public WebElement removeAttachment;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Activate Agreement')]")
    public WebElement activateAgreementButton;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[2]")
    public WebElement workerRatesTableWorkerType;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[3]/span")
    public List<WebElement> workerRatesTableWorkerSkill;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[4]")
    public WebElement workerRatesTableWorkerHourlyRate;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[5]/div[1]/div[2]")
    public WebElement workerRatesTableAgencyHourlyCostWithVat;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[5]/div[2]/div[2]")
    public WebElement workerRatesTableAgencyHourlyCostWithNoVat;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[6]")
    public WebElement workerRatesTableCareHiresHourlyCost;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[7]/div[1]/div[2]/b")
    public WebElement workerRatesTableFinalHourlyRateWithVat;

    @FindBy(xpath = "//h5[text()='Worker Rates']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[7]/div[2]/div[2]/b")
    public WebElement workerRatesTableFinalHourlyRateWithNoVat;

    @FindBy(xpath = "//label[text()='Worker Type']/..//button")
    public WebElement workerRatePopupWorkerType;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/..//button")
    public WebElement workerRatePopupSkills;

    @FindBy(xpath = "//input[@formcontrolname='workerHourlyRate']")
    public WebElement workerRatePopupHourlyRate;

    @FindBy(xpath = "//input[@formcontrolname='agencyHourlyMargin']")
    public WebElement workerRatePopupAgencyMargin;

    @FindBy(xpath = "//td[contains(@class, 'total-vat-column')]/p")
    public WebElement workerRatePopupAgencyVat;

    @FindBy(xpath = "//td[@class='vat-column']//div[1]/div[2]")
    public WebElement workerRatePopupAgencyCostWithVat;

    @FindBy(xpath = "//td[@class='vat-column']//div[2]/div[2]")
    public WebElement workerRatePopupAgencyCostWithNoVat;

    @FindBy(xpath = "//input[@formcontrolname='carehiresMarginRate']")
    public WebElement workerRatePopupChHourlyMargin;

    @FindBy(xpath = "//td[contains(@class, 'carehires-vat-column')]/p")
    public WebElement workerRatePopupChHourlyVat;

    @FindBy(xpath = "//td[contains(@class,'final-charge')]//div[1]/div[2]")
    public WebElement workerRatePopupFinalRateWithVat;

    @FindBy(xpath = "//td[contains(@class,'final-charge')]//div[2]/div[2]")
    public WebElement workerRatePopupFinalRateWithNoVat;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//nb-icon[@nbtooltip]")
    public WebElement sleepInRatesViewIcon;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[2]")
    public WebElement sleepInRatesTableWorkerType;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[3]")
    public WebElement sleepInRatesTableHourlyChargeRate;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[4]/div[1]/div[2]")
    public WebElement sleepInRatesTableAgencyHourlyCostWithVat;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[4]/div[2]/div[2]")
    public WebElement sleepInRatesTableAgencyHourlyCostWithNoVat;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[5]")
    public WebElement sleepInRatesTableCareHiresHourlyCost;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[6]/div[1]/div[2]/b")
    public WebElement sleepInRatesTableFinalHourlyRateWithVat;

    @FindBy(xpath = "//h5[text()='Sleep In Request']/ancestor::div[contains(@class, 'row mt')]//table//tr/td[6]/div[2]/div[2]/b")
    public WebElement sleepInRatesTableFinalHourlyRateWithNoVat;

    @FindBy(xpath = "//label[text()='Worker Type']/..//button")
    public WebElement sleepInRatesPopupWorkerType;

    @FindBy(xpath = "//input[@formcontrolname='workerHourlyRate']")
    public WebElement sleepInRatesPopupHourlyRate;

    @FindBy(xpath = "//input[@formcontrolname='agencyHourlyMargin']")
    public WebElement sleepInRatesPopupAgencyMargin;

    @FindBy(xpath = "//td[contains(@class, 'total-vat-column')]/p")
    public WebElement sleepInRatesPopupAgencyVat;

    @FindBy(xpath = "//td[@class='vat-column']//div[1]/div[2]")
    public WebElement sleepInRatesPopupAgencyCostWithVat;

    @FindBy(xpath = "//td[@class='vat-column']//div[2]/div[2]")
    public WebElement sleepInRatesPopupAgencyCostWithNoVat;

    @FindBy(xpath = "//input[@formcontrolname='carehiresMarginRate']")
    public WebElement sleepInRatesPopupChHourlyMargin;

    @FindBy(xpath = "//td[contains(@class, 'carehires-vat-column')]/p")
    public WebElement sleepInRatesPopupChHourlyVat;

    @FindBy(xpath = "//td[contains(@class,'final-charge')]//div[1]/div[2]")
    public WebElement sleepInRatesPopupFinalRateWithVat;

    @FindBy(xpath = "//td[contains(@class,'final-charge')]//div[2]/div[2]")
    public WebElement sleepInRatesPopupFinalRateWithNoVat;

    @FindBy(xpath = "//button[contains(text(), 'Deactivate')]")
    public WebElement deactivateButton;

        @FindBy(xpath = "//ch-agreement-deactivate-modal//button")
    public WebElement deactivateButtonInDeactivateConfirmPopup;

    @FindBy(xpath = "//button[contains(text(), 'Activate Agreement')]")
    public WebElement activeAgreementButton;

    @FindBy(xpath = "//button[contains(text(), 'Edit Agreement')]")
    public WebElement editAgreementButton;

    @FindBy(xpath = "//button[contains(text(), 'Edit Sites')]")
    public WebElement editSitesButton;

    @FindBy(xpath = "//p[contains(text(),'Automation Master')]/../..//nb-checkbox//span[contains(@class, 'checkbox')]")
    public WebElement removingSite;

    @FindBy(xpath = "//p[contains(text(),'Automation Master')]/../..//nb-checkbox//input")
    public WebElement manageSiteAddRemoveCheckbox;

    @FindBy(xpath = "//span[text()='Apply']")
    public WebElement applyButton;

    @FindBy(id = "effectiveFrom")
    public WebElement effectiveDateCalendar;

    @FindBy(xpath = "//nb-card-footer/button[contains(text(),'Save')]")
    public WebElement changeSummarySaveButton;

    @FindBy(xpath = "(//p[@class='text-icon'])[1]")
    public WebElement workerRatesThreeDots;

    @FindBy(xpath = "//div[@class='side-menu-action-container']//nb-icon[@icon='edit']")
    public WebElement editIcon;

    @FindBy(xpath = "//div[@class='side-menu-action-container']//nb-icon[contains(@icon,'trash')]")
    public WebElement deleteIcon;

    @FindBy(xpath = "//img[@nbtooltip='Cancel Changes']")
    public WebElement cancelChangesIcon;

    @FindBy(xpath = "(//button[contains(text(), 'Add')])[1]")
    public WebElement workerRatesAddButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement skillsDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    public WebElement hourlyRateInput(String rateType) {
        String xpath = String.format("//td[text()='%s']/..//input[@formcontrolname='workerHourlyRate']", rateType);
        return BasePage.getElement(xpath);
    }

    public WebElement agencyMarginInput(String rateType) {
        String xpath = String.format("//td[text()='%s']/..//input[@formcontrolname='agencyHourlyMargin']", rateType);
        return BasePage.getElement(xpath);
    }

    public WebElement chHourlyMarginInput(String rateType) {
        String xpath = String.format("//td[text()='%s']/..//input[@formcontrolname='carehiresMarginRate']", rateType);
        return BasePage.getElement(xpath);
    }

    public WebElement checkEnableRateCheckbox(String rateType) {
        String xpath = String.format("//div[text()='%s']/ancestor::tr/td[1]//input", rateType);
        return BasePage.getElement(xpath);
    }

    public WebElement enableRateCheckboxSpan(String rateType) {
        String xpath = String.format("//div[text()='%s']/ancestor::tr/td[1]//span[contains(@class, 'checkbox')]", rateType);
        return BasePage.getElement(xpath);
    }

    @FindBy(xpath = "//ch-agreement-worker-rate-modal//button[contains(text(), 'Add')]")
    public WebElement workerRatesPopupAddButton;

    public WebElement finalRateVat(String rateType) {
        String xpath = String.format("//div[text()='%s']/ancestor::tr//input[@formcontrolname='finalChargeRateVat']", rateType);
        return BasePage.getElement(xpath);
    }

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    @FindBy(xpath = "//ch-agreement-worker-rate-modal//button[contains(text(), 'Continue')]")
    public WebElement workerRatesPopupContinueButton;

    @FindBy(xpath = "//ch-agreement-worker-rate-modal//nb-icon[contains(@nbtooltip, 'Special Rate')]")
    public WebElement workerRatesPopupViewSpecialRateIcon;

    @FindBy(xpath = "//th[contains(text(), 'Cancellation Hours')]/ancestor::table//p[@class='text-icon']")
    public WebElement cancellationPolicyThreeDots;

    @FindBy(xpath = "//th[contains(text(), 'Cancellation Hours')]/ancestor::div[contains(@class, 'row')][1]//button")
    public WebElement cancellationPolicyAddButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='noOfHours']/button")
    public WebElement beforeJobStartDropdown;

    @FindBy(xpath = "//input[@formcontrolname='cancellatonFeePercentage']")
    public WebElement cancellationFeePercentage;

    @FindBy(xpath = "//input[@formcontrolname='careHiresSplit']")
    public WebElement careHiresSplit;

    @FindBy(xpath = "//input[@formcontrolname='agencySplit']")
    public WebElement agencySplit;

    @FindBy(xpath = "//input[@formcontrolname='agencySplit']//ancestor::form/..//button[contains(text(), 'Add')]")
    public WebElement cancellationPolicyPopupAddButton;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    @FindBy(xpath = "//th[contains(text(), 'Hourly Charge')]/ancestor::table//p[@class='text-icon']")
    public WebElement sleepInRequestThreeDots;

    @FindBy(xpath = "//th[contains(text(), 'Hourly Charge')]/ancestor::div[contains(@class, 'row')][1]//button")
    public WebElement sleepInRequestAddButton;

    @FindBy(xpath = "//ch-agreement-sleep-rates-modal//button[contains(text(), 'Continue')]")
    public WebElement sleepInRatesPopupContinueButton;

    @FindBy(xpath = "//ch-agreement-sleep-rates-modal//button[contains(text(), 'Add')]")
    public WebElement sleepInRatesPopupAddButton;

    @FindBy(xpath = "//ch-agreement-sleep-rates-modal//nb-icon[contains(@nbtooltip, 'Special Rate')]")
    public WebElement sleepInRatesPopupViewSpecialRateIcon;

    @FindBy(linkText = "Download Agreement")
    public WebElement downloadAgreement;

    @FindBy(xpath = "(//table[@class='table ch-table'])[1]//td[1]/div[2][contains(text(),'AGR')]")
    public WebElement firstId;

    @FindBy(id = "business-name")
    public WebElement searchInput;

    @FindBy(xpath = "//div[@class='result result-active']")
    public WebElement firstSearchedResult;

    public String getAgencyCostNoVat(String rateType) {
        return String.format("//div[contains(text(), '%s')]/ancestor::tr/td[5]/span/div[2]/div[2]", rateType);
    }

    public String getAgencyCostVat(String rateType) {
        return String.format("//div[contains(text(), '%s')]/ancestor::tr/td[5]/span/div[1]/div[2]", rateType);
    }

    public String getChHourlyMargin(String rateType) {
        return String.format("//div[contains(text(), '%s')]/ancestor::tr/td[6]", rateType);
    }

    public String getChHourlyVat(String rateType) {
        return String.format("//div[contains(text(), '%s')]/ancestor::tr/td[7]/p", rateType);
    }

    @FindBy(xpath = "//tr/td[10]//nb-icon[contains(@nbtooltip, 'Special')]")
    public WebElement workerRatesViewIcon;

    @FindBy(xpath = "//nb-card//div[@class='close']/img")
    public WebElement workerRatesCloseIcon;

    public String getFinalRateNoVat(String rateType) {
        return String.format("//div[contains(text(), '%s')]/ancestor::tr/td[8]/span/div[2]/div[2]", rateType);
    }
}
