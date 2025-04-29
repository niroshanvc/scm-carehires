package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderNotificationManagementActions;
import io.cucumber.java.en.And;

public class ProviderNotificationManagementSteps {

    ProviderNotificationManagementActions notificationManagement = new ProviderNotificationManagementActions();

    @And("Provider user moves to Notification Management and update data")
    public void moveToNotificationManagementAndUpdateData() {
        notificationManagement.updateNotificationData();
    }
}
