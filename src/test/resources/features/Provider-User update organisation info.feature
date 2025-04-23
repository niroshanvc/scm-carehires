@Regression-Edit
@ProviderUser
Feature: Login as a provider user and update organisation data

  Background: login to carehires
    Given User logins to carehires as a provider user

  @UpdateOrganization
  Scenario: Login as a provider user and update organisation data
    And User navigates to Organisation page
    And User updates provider profile data


    And User creates a provider in draft stage
    And User logins off from Carehires
    And User logins to carehires
    And User navigates to Provider View page
    And User searches previously created provider which is in Draft stage
    And User moves to Site and edit data
    And User moves to Worker Staff and edit data
    And User moves to Provider - User Management and edit data
    And User moves to Billing Information and edit data
    And User clicks on the Complete Profile button on the Contract Agreement page
    And User navigates to Provider View page
    When User finds recently created provider
    Then User verifies the provider profile status as Profile Complete
    And User clicks on the approve button on provider information page
    And User verifies the provider profile status as Approved