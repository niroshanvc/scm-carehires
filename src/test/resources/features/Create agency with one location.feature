Feature: Test CareHires create agency with one location


  Background: login to carehires
    Given User logins to carehires

  @Regression
  @CreateAgency_WithOneLocation
  Scenario: Create agency with one location
    Given User navigates to Agency Create page
    And User enters valid agency - basic information
    And User enters valid agency - Credit Service information
    And User adds agency business location
    And User adds agency staff data
    And User adds Billing Profile Management data
    And User adds User Management data
    And User clicks on the Complete Profile button on the Sub Contracting Agreement page
    And User navigates to Agency View page
    When User finds recently created agency
    Then User verifies the agency profile status as Profile Complete
    And User completes the profile approve status
    And User saves the organizational settings
    And User again navigates to the Agency Profile page
    And User completes the profile approve status
    And User verifies the agency profile status as Approved