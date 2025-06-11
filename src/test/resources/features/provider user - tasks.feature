@Regression
@ProviderUser
Feature: Login as a provider user and verify settings


  @ApproveTimesheet
  Scenario: Login as a provider user and verify job template settings
    Given User logins to carehires as a provider user
    When Provider User navigates to Tasks - Timesheets approval page
    And Provider User approves timesheet