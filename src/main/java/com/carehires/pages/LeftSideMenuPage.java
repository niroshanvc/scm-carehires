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

    @FindBy(xpath = "//img[@alt='Jobs']")
    public WebElement jobsIcon;
}