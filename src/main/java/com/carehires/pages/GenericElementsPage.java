package com.carehires.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GenericElementsPage {
    @FindBy(xpath = "//label[contains(text(), 'Phone Number')]/following-sibling::div//nb-select/button")
    public WebElement phoneNumberDropdown;

    @FindBy(xpath = "//p[contains(text(), 'Validate Email')]")
    public WebElement validateEmailTextLink;

    @FindBy(xpath = "//nb-calendar-view-mode/button")
    public WebElement monthAndYearDropdown;

    @FindBy(xpath = "//nb-calendar-day-picker//nb-calendar-picker//*[not(contains(@class, 'bounding')) and not(" +
            "contains(@class, 'disabled'))]/div")
    public List<WebElement> calendarDays;

    @FindBy(xpath = "//nb-calendar-picker//div")
    public List<WebElement> yearOptions;

    @FindBy(xpath = "//nb-calendar-pageable-navigation/button[contains(@class, 'prev-month')]")
    public WebElement previousYearButton;

    @FindBy(xpath = "//nb-calendar-pageable-navigation/button[contains(@class, 'next-month')]")
    public WebElement nextYearButton;

    @FindBy(xpath = "//nb-option-list[contains(@id, 'nb-autocomplete')]")
    public WebElement autoSuggestAddresses;

    @FindBy(xpath = "//nb-option[contains(@id, 'nb-option')]")
    public List<WebElement> addressOptions;

    // Method to return a dynamic By locator for the calendar month based on the parse month
    public By getMonthLocator(String selectedMonth) {
        return By.xpath("//nb-calendar-picker//div[contains(text(), '" + selectedMonth + "')]");
    }
}
