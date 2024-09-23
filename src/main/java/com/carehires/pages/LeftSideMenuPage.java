package com.carehires.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftSideMenuPage {
    @FindBy(xpath = "//img[@title='Agencies']")
    public WebElement agencies;

    @FindBy(xpath = "//img[@title='Agencies']/../..//p[text()='Create']")
    public WebElement agencyCreate;

    @FindBy(xpath = "//img[@title='Agencies']/../..//p[text()='View']")
    public WebElement agencyView;

    @FindBy(xpath = "//img[@title='Workers']")
    public WebElement workers;

    @FindBy(xpath = "//img[@title='Workers']/../..//p[text()='Create - Individual']")
    public WebElement workerCreateIndividual;

    @FindBy(xpath = "//img[@title='Providers']")
    public WebElement providers;

    @FindBy(xpath = "//img[@title='Providers']/../..//p[text()='Create']")
    public WebElement providerCreate;
}