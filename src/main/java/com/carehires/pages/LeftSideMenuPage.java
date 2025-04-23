package com.carehires.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftSideMenuPage {
    public static final By agenciesMainLink = By.xpath("//img[@title='Agencies']/..");

    public static final By agencyCreateSubLink = By.xpath("(//div[text()='Create'])[1]");

    public static final By agencyViewSubLink = By.xpath("(//div[text()='View'])[1]");

    public static final By workersMainLink = By.xpath("//img[@title='Workers']/..");

    public static final By workersCreateIndividual = By.xpath("//div[text()='Create - Individual']");

    public static final By workersViewSubLink = By.xpath("(//div[text()='View'])[2]");

    public static final By providersMainLink = By.xpath("//img[@title='Providers']/..");

    public static final By providersCreateSubLink = By.xpath("(//div[text()='Create'])[2]");

    public static final By providersViewSubLink = By.xpath("(//div[text()='View'])[3]");

    public static final By agreementsMainLink = By.xpath("//img[@title='Agreements']/..");

    public static final By agreementsCreateSubLink = By.xpath("(//div[text()='Create'])[3]");

    public static final By agreementsViewSubLink = By.xpath("(//div[text()='View'])[4]");

    public static final By agreementsOverviewSubLink = By.xpath("(//div[text()='Overview'])[1]");

    public static final By tasksMainLink = By.xpath("//img[@title='Tasks']/..");

    @FindBy(xpath = "//img[@title='Organisation']/..")
    public WebElement organisationMainLink;

    public static final By paymentAuthorisationsSubLink = By.xpath("//div[text()='Payment Authorisations']");

    @FindBy(xpath = "//img[@alt='Jobs']")
    public WebElement jobsIcon;

    @FindBy(xpath = "//img[@alt='Settings']/..")
    public String settingsIcon;
}