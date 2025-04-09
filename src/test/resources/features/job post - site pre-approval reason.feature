@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create job post - block booking

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Provider View page
    And User finds provider name equal to Automation Provider 709333


  @EnableVisibilityButNotUsing
  Scenario: Custom jobs /Without Reason/ Without Internal note
    And User enables visibility and disable mandatory of BioRevolution
    And User navigates to Jobs page
    And User moves to Post New Job page
    And User enters general job details
    When User enters Job Preferences without reason and internal notes
    Then User enters Job Summary

  @EnableVisibilityAndUsing
  Scenario: Custom jobs /With Reason/ With Internal note
    And User enables visibility and disable mandatory of BioRevolution
    And User navigates to Jobs page
    And User moves to Post New Job page
    And User enters general job details
    When User enters Job Preferences with reason and internal notes
    Then User enters Job Summary

  @EnableVisibilityAndMandatoryAndUsing
  Scenario: Custom jobs /With Reason/ With Internal note
    And User enables both visibility and mandatory of BioRevolution
    And User navigates to Jobs page
    And User moves to Post New Job page
    And User enters general job details
    When User enters Job Preferences with both reason and internal notes
    Then User enters Job Summary