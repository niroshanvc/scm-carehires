package com.carehires.actions.zoho;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ZohoMailAutomationWithASP {
    public static void main(String[] args) {
        // Set the path for the ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open Zoho Mail login page
            driver.get("https://www.zoho.com/mail/login.html");

            WebElement signIn = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class= 'zlogin-apps']")));
            signIn.click();

            // Locate the email field and enter your email
            WebElement emailField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.id("login_id")));
            emailField.sendKeys("carehires@zohomail.com");

            // Click the "Next" button
            WebElement nextButton = driver.findElement(By.id("nextbtn"));
            nextButton.click();

            // Wait for the password field to appear and enter your application-specific password
            WebElement passwordField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.id("password")));
            passwordField.sendKeys("8KATTY6usfbQ"); // Use the ASP here

            // Click the "Sign in" button
            nextButton.click();

            // Wait for the Inbox to load and click on it
            WebElement inboxFolder = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'Text') and text()='Inbox']")));
            inboxFolder.click();

            // Wait for the email list to load
            List<WebElement> emailList = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@data-action = 'subject']/span")));

            // Get the most recent email (first email in the list)
            WebElement mostRecentEmail = emailList.get(0);
            mostRecentEmail.click(); // Click to open the email

            // Wait for the email content to load
            WebElement emailContent = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-rmvstyle-oncpy]//table[@width and contains(@style, 'border')]")));

            // Extract and print the content of the email
            String emailBody = emailContent.getText();
            System.out.println("Email Content: " + emailBody);

            // If you need to extract specific information (e.g., URL, username, and password), you can use regex or string operations here.

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
