package com.carehires.pages.overivew;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderOverviewPage {

    @FindBy(xpath = "//a[@href='/dashboard/providers/organization?stage=2']")
    public WebElement totalSiteViewDetailsLink;

    @FindBy(xpath = "//h3")
    public WebElement pageHeader;

    public static final By pageHeaderByLocator = By.xpath("//h3");

    @FindBy(xpath = "//a[contains(text(),'Go to Financial Dashboard')]")
    public WebElement outstandingPaymentLink;

    @FindBy(xpath = "//div[@class='header-container']//p[@class='heading']")
    public WebElement financeHeader;

    public static final By financeHeaderByLocator = By.xpath("//div[@class='header-container']//" +
            "p[@class='heading']");

    @FindBy(xpath = "//img[contains(@src,'Group 3 (1)')]/../a")
    public WebElement jobSummaryCompletedViewJobsLink;

    public static final By jobsBreadcrumbByLocator = By.xpath("//div[@class='breadcrumb']//span[text()='Jobs']");

    @FindBy(xpath = "//button[contains(@class, 'btn--completed')]")
    public WebElement jobFilterByCompletedButton;

    @FindBy(xpath = "//img[contains(@src,'Group 3.png')]/../a")
    public WebElement jobSummaryAllOpenViewJobsLink;

    @FindBy(xpath = "//button[contains(@class, 'btn--open jb')]")
    public WebElement jobFilterByOpenButton;

    @FindBy(xpath = "//img[contains(@src,'Group 4')]/../a")
    public WebElement jobSummaryOpenOverdueViewJobsLink;

    @FindBy(xpath = "//button[contains(@class, 'btn--overdue')]")
    public WebElement jobFilterByOpenOverdueButton;

    @FindBy(xpath = "//img[contains(@src,'Group 5')]/../a")
    public WebElement jobSummarySuggestedAllocateWorkersLink;

    @FindBy(xpath = "//button[contains(@class, 'btn--suggested')]")
    public WebElement jobFilterBySuggestedButton;

    @FindBy(xpath = "//img[contains(@src,'Group 6')]/../a")
    public WebElement jobSummaryAllocatedReviewTimesheetsLink;

    @FindBy(xpath = "//button[contains(@class, 'btn--allocated')]")
    public WebElement jobFilterByAllocatedButton;

    @FindBy(xpath = "//img[contains(@src,'Group.png')]/parent::h3[contains(text(), 'Raise')]")
    public WebElement raiseATicketLink;

    @FindBy(xpath = "//img[contains(@src,'Group.png')]/parent::h3[contains(text(), 'base')]")
    public WebElement knowledgeBaseLink;

    @FindBy(xpath = "//img[contains(@src,'Group.png')]/parent::h3[contains(text(), 'Email')]")
    public WebElement emailUsLink;
}
