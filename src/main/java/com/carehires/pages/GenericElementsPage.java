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

    @FindBy(xpath = "//nb-calendar-day-picker")
    public WebElement calendarPopup;

    // Method to return a dynamic By locator for the calendar date based on the passed date
    public By getDateLocator(String selectedDate) {
        return By.xpath("//div[@class='days-container']//nb-calendar-day-cell[not(contains(@class, 'month'))]/div[text()=' " + selectedDate + " ']");
    }

    public static final String MONTH_YEAR_XPATH = "//nb-calendar-view-mode/button";

    @FindBy(xpath = "//nb-calendar-view-mode/button")
    public WebElement monthAndYear;

    @FindBy(xpath = "//button[contains(@class, 'next-month')]")
    public WebElement nextMonthButton;

    @FindBy(xpath = "//button[contains(@class, 'prev-month')]")
    public WebElement previousMonthButton;

    @FindBy(xpath = "//nb-calendar-day-cell[not(contains(@class, 'bounding')) and not(contains(@class, 'disabled'))]/div")
    public List<WebElement> calendarDays;
}
