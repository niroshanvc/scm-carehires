package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgreementOverviewPage {

    @FindBy(xpath = "//div[contains(text(), 'Total Agreements')]")
    public WebElement totalAgreements;

    @FindBy(xpath = "//nav[@aria-label='breadcrumb']/ol/li[3]")
    public WebElement thirdBreadcrumb;

    @FindBy(xpath = "//nav[@aria-label='breadcrumb']/ol/li[2]/a")
    public WebElement overviewBreadcrumb;

    @FindBy(xpath = "//div[contains(@class,'table-row body')][1]//span")
    public WebElement issueAgreementLink;

    @FindBy(xpath = "//div[contains(@class, 'agreement-overview-header')]/h3")
    public WebElement agreementOverviewHeader;
}
