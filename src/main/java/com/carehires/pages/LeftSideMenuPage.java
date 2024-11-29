package com.carehires.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftSideMenuPage {
    public static final By agenciesMainLink = By.xpath("//img[@title='Agencies']");

    public static final By createSubLink = By.xpath("//div[text()='Create']");

    public static final By viewSubLink = By.xpath("//div[text()='View']");

    public static final By workersMainLink = By.xpath("//img[@title='Workers']");

    public static final By workerCreateIndividual = By.xpath("//div[text()='Create - Individual']");

    public static final By providersMainLink = By.xpath("//img[@title='Providers']");

    @FindBy(xpath = "//img[@alt='Jobs']")
    public WebElement jobsIcon;
}