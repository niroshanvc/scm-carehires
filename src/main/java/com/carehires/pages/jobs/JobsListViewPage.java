package com.carehires.pages.jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobsListViewPage {

    @FindBy(xpath = "(//ch-job-list-item)[1]/div[2]")
    public WebElement firstJobId;

    public static final By firstThreeDots = By.xpath("(//ch-job-list-item)[1]//button");

    public static final By viewDetailedJobInfo = By.xpath("//nb-context-menu//a[@title='View Detailed Job Info']");

    public static final By copyJobDetails = By.xpath("//nb-context-menu//a[@title='Copy Job Details']");

    public static final By chAdminNote = By.xpath("//nb-context-menu//a[@title='CH Admin Note']");

    public static final By manageAllocations = By.xpath("//nb-context-menu//a[@title='Manage Allocations']");

    public static final By cancelVacancy = By.xpath("//nb-context-menu//a[@title='Cancel Vacancy']");

    public static final By detailViewThreeDots = By.xpath("//span[contains(@class, 'menu-wrapper')]");

    public static final By detailViewCopyJobDetails = By.xpath("//span[contains(@class, 'menu-wrapper')]//li[1]/img");

    public static final By detailViewAuditLog = By.xpath("//span[contains(@class, 'menu-wrapper')]//li[2]/img");

    public static final By detailViewCancelJob = By.xpath("//span[contains(@class, 'menu-wrapper')]//li[3]/img");

    @FindBy(xpath = "//button[contains(text(),'Time Sheets')]")
    public WebElement detailViewTimeSheetsButton;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Eligible Workers']")
    public WebElement detailViewEligibleWorkersTab;

    @FindBy(xpath = "//nb-tab[@tabtitle='Eligible Workers']//nb-select[@placeholder='Filter By Agency']/button")
    public WebElement detailViewEligibleWorkersFilterByAgencyDropdown;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Suggested Workers']")
    public WebElement detailViewSuggestedWorkersTab;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Selected Workers']")
    public WebElement detailViewSelectedWorkersTab;

    @FindBy(xpath = "//nb-tab[@tabtitle='Selected Workers']//button[contains(text(),'Reject')]")
    public WebElement detailViewSelectedWorkersRejectButton;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Rejected Workers']")
    public WebElement detailViewRejectedWorkersTab;
}
