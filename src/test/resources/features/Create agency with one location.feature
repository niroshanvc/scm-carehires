Feature: Test CareHires create agency with one location


  Background: login to carehires
    Given User logins to carehires

  @Regression
  @CreateAgency_WithOneLocation
  Scenario: Create agency with one location
    Given User navigates to Agency Create page
    When User enters valid agency - basic information
    And User enters valid agency - Credit Service information
    And User adds agency business location
    And User adds agency staff data
    And User adds Billing Profile Management data
    And User adds User Management data
