package com.carehires.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GenericElementsPage {
    @FindBy(xpath = "//label[contains(text(), 'Phone Number')]/following-sibling::div//nb-select/button")
    public WebElement phoneNumberDropdown;

    @FindBy(id = "nb-option-4")
    public WebElement mobile;

    @FindBy(xpath = "//p[contains(text(), 'Validate Email')]")
    public WebElement validateEmailTextLink;

    @FindBy(xpath = "//nb-calendar-day-picker")
    public WebElement calendarPopup;

    // Method to return a dynamic By locator for the calendar date based on the passed date
    public By getDateLocator(String selectedDate) {
        return By.xpath("//div[@class='days-container']//nb-calendar-day-cell[not(contains(@class, 'month'))]/div[text()=' " + selectedDate + " ']");
    }
}
