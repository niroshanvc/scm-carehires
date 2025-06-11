package com.carehires.pages.tasks;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TimesheetApprovalsPage {

    @FindBy(xpath = "//div[@class='list-container']/div[1]//button[@status='success']")
    public WebElement approveTimesheetButton;
}
