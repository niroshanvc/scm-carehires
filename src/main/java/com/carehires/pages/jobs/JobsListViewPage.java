package com.carehires.pages.jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobsListViewPage {

    @FindBy(xpath = "(//ch-job-list-item)[1]/div[2]")
    public WebElement firstJobId;

    @FindBy(xpath = "(//ch-job-list-item)[1]//button")
    public WebElement firstThreeDotsElement;

    public static final By firstThreeDots = By.xpath("(//ch-job-list-item)[1]//button");

    public static final By jobDetailsPopupThreeDots = By.xpath("//span[contains(@class, 'menu-wrapper')]//" +
            "p");

    public static final By viewDetailedJobInfo = By.xpath("//nb-context-menu//a[@title='View Detailed Job " +
            "Info']");

    public static final By copyJobDetails = By.xpath("//nb-context-menu//a[@title='Copy Job Details']");

    public static final By jobDetailsPopupCopyJobDetails = By.xpath("//div[contains(@class, 'side-menu')]//" +
            "span[contains(text(), 'Copy Job Details')]");

    public static final By jobDetailsPopupAuditLog = By.xpath("//div[contains(@class, 'side-menu')]//" +
            "span[contains(text(), 'Audit Log')]");

    public static final By jobDetailsPopupCancelJob = By.xpath("//div[contains(@class, 'side-menu')]//" +
            "span[contains(text(), 'Cancel Job')]");

    public static final By chAdminNote = By.xpath("//nb-context-menu//a[@title='CH Admin Note']");

    public static final By manageAllocations = By.xpath("//nb-context-menu//a[@title='Manage Allocations']");

    public static final By cancelJob = By.xpath("//nb-context-menu//a[@title='Cancel Job']");

    public static final By detailViewThreeDots = By.xpath("//span[contains(@class, 'menu-wrapper')]");

    public static final By detailViewCopyJobDetails = By.xpath("//span[contains(@class, 'menu-wrapper')]//" +
            "li[1]/img");

    public static final By detailViewAuditLog = By.xpath("//span[contains(@class, 'menu-wrapper')]//li[2]/img");

    public static final By detailViewCancelJob = By.xpath("//span[contains(@class, 'menu-wrapper')]//li[3]/img");

    @FindBy(xpath = "//button[contains(text(),'Timesheets')]")
    public WebElement detailViewTimeSheetsButton;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Eligible Workers']")
    public WebElement detailViewEligibleWorkersTab;

    @FindBy(xpath = "//nb-tab[contains(@class,'content-active')]//nb-select[@placeholder='Filter By Agency']/button")
    public WebElement detailViewWorkersFilterByAgencyDropdown;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Suggested Workers']")
    public WebElement detailViewSuggestedWorkersTab;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Selected Workers']")
    public WebElement detailViewSelectedWorkersTab;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Rejected Workers']")
    public WebElement detailViewRejectedWorkersTab;

    @FindBy(xpath = "//nb-tab[contains(@class,'content-active')]//button[contains(text(),'Reject')]")
    public WebElement rejectButtonOnViewJobDetailPopup;

    @FindBy(xpath = "//nb-tab[contains(@class,'content-active')]//button[contains(text(),'Select')]")
    public WebElement selectButtonOnViewJobDetailPopup;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    @FindBy(xpath = "(//div[contains(@class, 'worker-container')]//p[@class='ch-job-list-Details'])[1]")
    public WebElement allocatedWorkerName;

    @FindBy(xpath = "(//div[contains(@class, 'worker-container')]//div[img[@class = 'time-icon']])[1]")
    public WebElement jobStartDate;

    @FindBy(xpath = "(//div[contains(@class, 'worker-container')]//div[img[@class = 'time-icon']])[3]")
    public WebElement jobStartTime;

    @FindBy(xpath = "(//div[contains(@class, 'worker-container')]//div[img[@class = 'time-icon']])[2]")
    public WebElement jobEndDate;

    @FindBy(xpath = "(//div[contains(@class, 'worker-container')]//div[img[@class = 'time-icon']])[4]")
    public WebElement jobEndTime;

    @FindBy(id = "business-name")
    public WebElement searchByJobIdInput;

    @FindBy(xpath = "//input[@placeholder='Date Range']")
    public WebElement dateRangeInput;

    @FindBy(xpath = "//nb-select[@id='provider-id']/button")
    public WebElement providerDropdown;

    @FindBy(id = "provider-id")
    public WebElement providerInput;

    @FindBy(xpath = "//nb-select[@placeholder='Site']/button")
    public WebElement siteDropdown;

    @FindBy(xpath = "//input[@id='carehome-id']")
    public WebElement siteInput;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[1]")
    public WebElement allStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[2]")
    public WebElement openUrgentStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[3]")
    public WebElement openStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[4]")
    public WebElement openOverdueStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[5]")
    public WebElement suggestedStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[6]")
    public WebElement allocatedStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[7]")
    public WebElement completedStatusButton;

    @FindBy(xpath = "//div[contains(@class, 'btn')]/button[8]")
    public WebElement cancelledStatusButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='reason']/button")
    public WebElement reasonDropdownButtonOnCancelJobPopup;

    @FindBy(xpath = "//textarea[@formcontrolname='description']")
    public WebElement descriptionTextAreaOnCancelJobsPopup;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButtonOnCancelJobPopup;

    @FindBy(xpath = "//span[contains(text(), 'Suggest')]/parent::button")
    public List<WebElement> suggestButtonOnJobDetailsPopup;

    @FindBy(xpath = "//p[contains(text(), 'Suggest this worker')]/parent::button")
    public WebElement suggestThisWorkerButton;

    @FindBy(xpath = "//div[contains(@class, 'ch-job-list-Header')]/../div[3]")
    public WebElement finishSearch;

    @FindBy(xpath = "//ch-job-list-item/div[contains(@id, 'item')]//div[contains(@style, 'inline')]/span[contains" +
            "(@class, 'jobType badge')]")
    public List<WebElement> jobStatuses;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//nb-dialog-container//nb-card/nb-card-body//textarea")
    public WebElement reasonToRejectTextareaOnSuggestedWorkerRejectPopup;

    @FindBy(xpath = "//nb-tab[contains(@class,'content-active')]//button[contains(text(), 'Select')]")
    public WebElement selectButtonOnDetailViewTab;

    @FindBy(xpath = "//nb-dialog-container//nb-card/nb-card-footer//button")
    public WebElement rejectButtonOnRejectPopup;

    @FindBy(xpath = "//button[contains(text(),'Submit Timesheet')]")
    public WebElement detailViewSubmitTimesheetButtonOn;

    @FindBy(xpath = "//input[@id='start-date']")
    public WebElement startDateInput;

    @FindBy(xpath = "//input[@id='start-time']")
    public WebElement startTimeInput;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement startTimeAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availableStartTimes;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement startTimeSelectionOkButton;

    @FindBy(id = "end-date")
    public WebElement endDateInput;

    @FindBy(xpath = "//input[@id='end-time']")
    public WebElement endTimeInput;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement endTimeAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availableEndTimes;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement endTimeSelectionOkButton;

    @FindBy(xpath = "//textarea")
    public WebElement reasonToAmendTimesheetTextarea;

    @FindBy(id = "paidDuration")
    public WebElement paidBreakDurationInput;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement paidBreakDurationAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availablePaidBreakDuration;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement paidBreakDurationSelectionOkButton;

    @FindBy(id = "unpaidDuration")
    public WebElement unpaidBreakDurationInput;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement unpaidBreakDurationAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availableUnpaidBreakDuration;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement unpaidBreakDurationSelectionOkButton;

    @FindBy(id = "paidNote")
    public WebElement paidBreakNoteInput;

    @FindBy(id = "unpaidNote")
    public WebElement unpaidBreakNoteInput;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement proofDocument;

    @FindBy(xpath = "//nb-card/nb-card-footer//button[contains(text(), 'Update')]")
    public WebElement timeSheetUpdateButton;

    @FindBy(xpath = "//button[contains(text(),'Approve Timesheet')]")
    public WebElement detailViewApproveTimesheetButtonOn;

    @FindBy(xpath = "//button[contains(text(),'Dispute Timesheet')]")
    public WebElement detailViewDisputeTimesheetButtonOn;

    @FindBy(xpath = "//nb-card//button")
    public WebElement approvePopupApproveButton;

    @FindBy(xpath = "//nb-card//textarea")
    public WebElement reasonToDisputeTextarea;

    @FindBy(xpath = "//nb-card//button")
    public WebElement disputePopupDisputeButton;

    @FindBy(xpath = "(//ch-job-list-item)[1]/div[2]//p[contains(@class, 'job-id')]//img")
    public WebElement firstCopyJobIdIcon;

    @FindBy(xpath = "//button[text()='Cancel Job']")
    public WebElement cancelJobButton;

    @FindBy(xpath = "//quill-editor//p")
    public WebElement cheAdminNoteInput;

    @FindBy(xpath = "//nb-card/nb-card-footer//button")
    public WebElement chAdminNoteSaveButton;

    @FindBy(xpath = "//button[@nbcontextmenutrigger='hover']/preceding-sibling::div/div/span[contains(@style, 'blue')]" +
            "/following-sibling::span")
    public WebElement jobViewChAdminNote;

    public static final By modifyCancellationReason = By.xpath("//nb-context-menu//a[@title=" +
            "'Modify Cancellation Reason']");

    @FindBy(xpath = "//nb-select[@formcontrolname='reason']/button")
    public WebElement reasonDropdownButtonOnModifyCancellationReason;

    @FindBy(xpath = "//nb-card//button[contains(@class, 'primary')]")
    public WebElement modifyCancellationReasonSaveButton;

    @FindBy(xpath = "//nb-card//form//textarea")
    public WebElement statementOfWorkNoteTextarea;

    @FindBy(xpath = "//nb-card//form//input[@type='checkbox']")
    public WebElement statementOfWorkConfirmCheckbox;

    @FindBy(xpath = "//nb-card//form//nb-checkbox")
    public WebElement statementOfWorkCheckboxSelectOrNot;

    @FindBy(xpath = "//nb-card/nb-card-footer//button[contains(text(), 'Select')]")
    public WebElement statementOfWorkSelectButton;

    @FindBy(xpath = "(//h4[contains(@class,'hourly-charge-rate')])[1]")
    public WebElement suggestedWorkerHourlyRate;

    @FindBy(xpath = "(//h5[contains(@class,'worker-type-name')])[1]")
    public WebElement suggestedWorkerAgencyCharge;

    @FindBy(xpath = "(//h5[contains(@class,'worker-type-name')])[2]")
    public WebElement suggestedWorkerCareHiresMargin;

    @FindBy(xpath = "(//h5[contains(@class,'worker-type-name')])[3]")
    public WebElement suggestedWorkerCareHiresVat;

    public static final By jobDetailsPopupConvertToOpen = By.xpath("//div[contains(@class, 'side-menu')]" +
            "//span[contains(text(), 'Convert To Open')]");

    @FindBy(xpath = "//nb-card[@class='confirm-card']//button")
    public WebElement convertToOpenOkButton;

    @FindBy(xpath = "//span[starts-with(@class, 'jobType') and contains(@class, 'open ')]/..//p")
    public WebElement notAllocated;

    @FindBy(xpath = "//div[@class='proof-preview']")
    public WebElement proofDocumentArea;

    @FindBy(xpath = "//div[@class='proof-preview']//button[@status='danger']")
    public WebElement removeAttachmentButton;

    @FindBy(xpath = "//ch-job-detail-view//div[@class='close']/img")
    public WebElement jobDetailsPopupCloseIcon;
}
