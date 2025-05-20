@Regression
@Job
@SuperAdminUser
Feature: Test CareHires job post templates

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page


  @InactiveAndActiveTemplate
  Scenario: Inactive job post template
    And User moves to Post New Job page
    And User enters Job Details to manage job template
    And User enters Job Preferences to manage job template
    And User create a new template by creating a new job post
    And User navigates to Settings page
    And User moves to job templates page
    When User searches template by name
    Then User makes job template inactive
    And User makes job template active
