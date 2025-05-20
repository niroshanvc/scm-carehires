@Regression
@Provider
@SuperAdminUser
Feature: Test CareHires create provider with one location

  Background: login to carehires
    Given User logins to carehires

  @CreateProvider
  Scenario: Create agency with one location
    When User navigates to Provider Create page
    And User enters valid provider - company information
    And User enters valid provider - site management data
    When User enters valid worker staff data
    Then User verifies calculated Monthly agency spend value
    And User creates a new worker staff
    And User verifies calculated Monthly spend value displays in the table grid and moves to the next screen
    And User enters valid provider - user management data
    And User enters General Billing Information
    And User clicks on the Complete Profile button on the Contract Agreement page
    And User navigates to Provider View page
    When User finds recently created provider
    Then User verifies the provider profile status as Profile Complete
    And User clicks on the approve button on provider information page
    And User verifies the provider profile status as Approved

  @CreateProviderWithCustomBilling
  Scenario: Create agency with one location
    When User navigates to Provider Create page
    And User enters valid provider - company information
    And User enters valid provider - site management data
    When User enters valid worker staff data
    Then User verifies calculated Monthly agency spend value
    And User creates a new worker staff
    And User verifies calculated Monthly spend value displays in the table grid and moves to the next screen
    And User enters valid provider - user management data
    And User enters General and Custom Billing Information
    And User clicks on the Complete Profile button on the Contract Agreement page
    And User navigates to Provider View page
    When User finds recently created provider
    Then User verifies the provider profile status as Profile Complete
    And User clicks on the approve button on provider information page
    And User verifies the provider profile status as Approved