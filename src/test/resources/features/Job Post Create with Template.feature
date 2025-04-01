@Regression-Create
@Job
@SuperAdminUser
@PostGeneralJobsTemplate2
Feature: Test CareHires create job post using template

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page


  @PostGeneralJobsTemplate2A
  Scenario: Jobs using templates / Without recurrence / Without breaks / No Skills / No Block booking / No job notes
    And User selects Post using Template and select the template as Job Post - Automation 001
    And User enters Job Duration by disabling both recurrence and breaks
    When User enters Job Preferences without Job note and disable block booking
    Then User verifies shift summary for job post template - scenario1
    And User enters Job Summary from template

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