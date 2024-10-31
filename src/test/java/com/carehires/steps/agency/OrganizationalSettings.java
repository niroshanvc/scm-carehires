package com.carehires.steps.agency;

import com.carehires.actions.agency.OrganizationalSettingsActions;
import io.cucumber.java.en.And;

public class OrganizationalSettings {

    OrganizationalSettingsActions orgSettings = new OrganizationalSettingsActions();

    @And("User saves the organizational settings")
    public void saveSettings() {
        orgSettings.saveSettings();
    }
}