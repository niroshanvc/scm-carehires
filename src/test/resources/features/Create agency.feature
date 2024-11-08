@Regression
@Agency
@SuperAdminUser
Feature: Test CareHires create agency with no VAT Registered, one location

  Background: login to carehires
    Given User logins to carehires

  @CreateAgency_WithOneLocation
  Scenario: Create agency with one location
    When User navigates to Agency Create page
    And User enters valid agency - basic information
    And User enters valid agency - Credit Service information
    And User adds agency business location
#    add multiple locations
    And User adds agency staff data
    And User adds Billing Profile Management data
    And User adds User Management data
    And User clicks on the Complete Profile button on the Sub Contracting Agreement page
    And User navigates to Agency View pag
    When User finds recently created agency
    Then User verifies the agency profile status as Profile Complete
    And User completes the profile approve status
#    And User saves the organizational settings
    And User again navigates to the Agency Profile page
#    And User approves the profile approve status
    And User verifies the agency profile status as Approved