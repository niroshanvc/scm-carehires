package com.carehires.actions;

import com.carehires.pages.LeftSideMenuPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

public class LeftSideMenuActions {
    LeftSideMenuPage leftSideMenu;

    public LeftSideMenuActions() {
        leftSideMenu = new LeftSideMenuPage();
        PageFactory.initElements(BasePage.getDriver(), leftSideMenu);
    }

    public void gotoAgencyCreatePage() {
        BasePage.mouseHoverAndClick(leftSideMenu.agencies, leftSideMenu.agencyCreate);
    }
}
