Feature: Test CareHires create agency with one location


  Background: login to carehires
    Given User logins to carehires

  @Regression
  @CreateAgency_WithOneLocation
  Scenario: Create agency with one location
    Given User navigates to Provider Create page
    And User enters valid provider - company information