@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires job post templates

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page


  @InactiveTemplate
  Scenario: Inactive job post template
    And User moves to Post New Job page
    And User enters Job Details to manage job template
    And User enters Job Preferences to manage job template
    And User create a new template by creating a new job post
    And User navigates to Settings page
    And User moves to job templates page
    When User searches template by name
    Then User makes job template inactive

  @PostGeneralJobsTemplate2B
  Scenario: Jobs using templates / With recurrence / With breaks / With Skills / With Block booking / With job notes
    And User selects Post using Template and select the template as Job Post - Automation 002
    And User enters Job Duration by enabling both recurrence and breaks
    When User clicks on Continue button on Job Preferences page
    Then User enters Job Summary from template

  @PostGeneralJobsTemplate2C
  Scenario: Jobs using templates / With recurrence / Without breaks / No Skills / No Block booking / No job notes
    And User selects Post using Template and select the template as Job Post - Automation 001
    And User enters Job Duration by enabling recurrence only
    When User enters Job Preferences without Job note and disable block booking
    Then User enters Job Summary from template

  @PostGeneralJobsTemplate2D
  Scenario: Jobs using templates / With recurrence / With breaks / No Skills / No Block booking / No job notes
    And User selects Post using Template and select the template as Job Post - Automation 001
    And User enters Job Duration by enabling both recurrence and breaks
    When User enters Job Preferences without Job note and disable block booking
    Then User enters Job Summary from template

  @PostGeneralJobsTemplate2E
  Scenario: Jobs using templates / With recurrence / With breaks / With Skills / No Block booking / No job notes
    And User selects Post using Template and select the template as Job Post - Automation 002
    And User enters Job Duration by enabling both recurrence and breaks
    When User enters Job Preferences without Job note and disable block booking
    Then User enters Job Summary from template

  @PostGeneralJobsTemplate2F
  Scenario: Jobs using templates / With recurrence / With breaks / With Skills / With Block booking / No job notes
    And User selects Post using Template and select the template as Job Post - Automation 002
    And User enters Job Duration by enabling both recurrence and breaks
    When User enters Job Preferences without Job note
    Then User enters Job Summary from template

  @PostGeneralJobsTemplate2G
  Scenario: Jobs using templates / With recurrence / With breaks / With Skills / With Block booking / With job notes
    And User selects Post using Template and select the template as Job Post - Automation 002
    And User enters Job Duration by enabling both recurrence and breaks
    When User clicks on Continue button on Job Preferences page
    Then User enters Job Summary from template