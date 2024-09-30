Feature: Test CareHires create agency with one location


  Background: login to carehires
    Given User logins to carehires

  @Regression
  @CreateProvider
  Scenario: Create agency with one location
    Given User navigates to Provider Create page
    And User enters valid provider - company information
    And User enters valid provider - site management data
    When User enters valid worker staff data
    Then User verifies calculated Monthly agency spend value
    And User creates a new worker staff
    And User verifies calculated Monthly spend value displays in the table grid and moves to the next screen
    And User enters valid provider - user management data

