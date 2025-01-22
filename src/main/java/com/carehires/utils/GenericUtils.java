package com.carehires.utils;

import com.carehires.pages.GenericElementsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericUtils {

    GenericElementsPage genericElementsPage;

    public GenericUtils() throws BasePage.WebDriverInitializationException {
        genericElementsPage = new GenericElementsPage();
        PageFactory.initElements(BasePage.getDriver(), genericElementsPage);
    }

    private static final Logger logger = LogManager.getLogger(GenericUtils.class);

    private static WebDriver getDriverInstance() throws BasePage.WebDriverInitializationException {
        return BasePage.getDriver();
    }

    public void fillAddress(WebElement postcodeInput, String postcode, int delayInMilliseconds) {
        logger.info("fillAddress");
        BasePage.clearTexts(postcodeInput);
        BasePage.typeWithStringBuilderAndDelay(postcodeInput, postcode, delayInMilliseconds);
        BasePage.waitUntilElementPresent(genericElementsPage.autoSuggestAddresses, 60);
        List<WebElement> addresses = null;
        try {
            addresses = getDriverInstance().findElements(By.xpath("//nb-option[contains(@id, 'nb-option')]"));
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }

        if (!addresses.isEmpty()) {
            addresses.get(1).click();
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    public void fillPhoneNumber(String entity, String ymlFile, WebElement phoneNumberInput, String... keys) {
        logger.info("fillPhoneNumber .........");

        for (String key : keys) {
            if (Objects.equals(key, "PhoneNumberType")) {
                String phoneNumberType = DataConfigurationReader.readDataFromYmlFile(entity, ymlFile, keys);
                BasePage.waitUntilElementPresent(genericElementsPage.phoneNumberDropdown, 20);
                BasePage.clickWithJavaScript(genericElementsPage.phoneNumberDropdown);
                BasePage.clickWithJavaScript(getPhoneNumberTypeXpath(phoneNumberType));
            }
            if (Objects.equals(key, "PhoneNumber")) {
                String phoneNumber = DataConfigurationReader.readDataFromYmlFile(entity, ymlFile, keys);
                BasePage.clearAndEnterTexts(phoneNumberInput, phoneNumber);
            }
        }
    }

    public void waitUntilEmailAddressValidatedForUniqueness(String emailAddress, WebElement emailAddressInput,
                                                            WebElement fieldToBeDisplayed) {
        logger.info("waitUntilEmailAddressValidatedForUniqueness");

        BasePage.waitUntilElementPresent(emailAddressInput, 60);
        BasePage.typeWithStringBuilder(emailAddressInput, emailAddress);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(genericElementsPage.validateEmailTextLink);

        try {
            BasePage.waitUntilElementPresent(fieldToBeDisplayed, 60);
        } catch (ElementNotInteractableException e) {
            // Throw a dedicated exception with a more specific message
            throw new EmailAddressNotUniqueException("Email address is not guaranteed to be unique. " +
                    "Element remained hidden after validation.");
        }
    }

    // Define a dedicated exception for email address uniqueness issue
    public static class EmailAddressNotUniqueException extends RuntimeException {
        public EmailAddressNotUniqueException(String message) {
            super(message);
        }
    }

    private String getPhoneNumberTypeXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    public void selectDateFromCalendarPopup(String targetDate) {
        try {
            // Parse the target date to get day, month, and year
            LocalDate target = LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("dd MMM yyyy"));
            int targetDay = target.getDayOfMonth();
            String targetMonth = target.format(DateTimeFormatter.ofPattern("MMM"));  // e.g., "Oct"
            int targetYear = target.getYear();

            // Step 1: Open the month/year dropdown
            BasePage.clickWithJavaScript(genericElementsPage.monthAndYearDropdown);

            // Step 2: Select the year
            selectYear(targetYear);

            // Step 3: Select the month
            selectMonth(targetMonth);

            // Step 4: Select the day
            selectDay(targetDay);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}. Expected format is 'dd MMM yyyy'", targetDate, e);
            throw e;
        }

    }

    // Helper method to select the year
    private void selectYear(int targetYear) {
        while (true) {
            List<WebElement> yearsDisplayed = genericElementsPage.yearOptions; // Update locator as per dropdown structure
            int firstYearDisplayed = Integer.parseInt(yearsDisplayed.get(0).getText().trim());
            int lastYearDisplayed = Integer.parseInt(yearsDisplayed.get(yearsDisplayed.size() - 1).getText().trim());

            if (targetYear >= firstYearDisplayed && targetYear <= lastYearDisplayed) {
                for (WebElement yearOption : yearsDisplayed) {
                    if (Integer.parseInt(yearOption.getText().trim()) == targetYear) {
                        BasePage.clickWithJavaScript(yearOption);
                        return;
                    }
                }
            } else if (targetYear < firstYearDisplayed) {
                BasePage.clickWithJavaScript(genericElementsPage.previousYearButton);
            } else {
                BasePage.clickWithJavaScript(genericElementsPage.nextYearButton);
            }
        }
    }

    // Helper method to select the month
    private void selectMonth(String targetMonth) {
        WebElement monthElement = null;
        try {
            monthElement = BasePage.getDriver().findElement(genericElementsPage.getMonthLocator(targetMonth));
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
        BasePage.clickWithJavaScript(monthElement);
    }

    // Helper method to select the day
    private void selectDay(int targetDay) {
        String targetDayString = String.valueOf(targetDay);
        for (WebElement dayElement : genericElementsPage.calendarDays) {
            if (BasePage.getText(dayElement).trim().equals(targetDayString)) {
                BasePage.clickWithJavaScript(dayElement);
                break;
            }
        }
    }

    public List<String> getSelectedValues(List<WebElement> elements) {
        // Retrieve elements representing selected skills
        List<String> selectedValues = new ArrayList<>();
        for (WebElement el : elements) {
            selectedValues.add(el.getText());
        }

        return selectedValues;
    }
}
