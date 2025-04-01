@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create sleep in job post using template

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @PostSleepInJobUsingTemplate1A
  Scenario: Custom jobs / Post 1 vacancy / No Skills / No Block booking / No job notes
    And User selects Post using Template and select the sleep in template as Sleep in - Automation 003
    And User enters sleep in duration and recurrence
    When User selects gender and clicks on Continue button
    And User enters Job Summary from template

  @PostSleepInJobUsingTemplate1B
  Scenario: Custom jobs / Post more than 1 vacancy / With Skills / No Block booking / No job notes
    And User selects Post using Template and select the sleep in template as Sleep in - Automation 004
    And User selects more than one vacancy and enters sleep in duration and recurrence data
    When User clears Job note and disabling block booking
    And User enters Job Summary from template

  @PostSleepInJobUsingTemplate1C
  Scenario: Custom jobs / With Skills / With Block booking / No job notes
    And User selects Post using Template and select the sleep in template as Sleep in - Automation 004
    And User enters sleep in duration and recurrence
    When User clears Job note and enabling block booking
    And User enters Job Summary from template

  @PostSleepInJobUsingTemplate1D
  Scenario: Custom jobs / With Skills / With Block booking / With job notes
    And User selects Post using Template and select the sleep in template as Sleep in - Automation 004
    And User enters sleep in duration and recurrence
    And enters job note and click on continue button
    And User enters Job Summary from template