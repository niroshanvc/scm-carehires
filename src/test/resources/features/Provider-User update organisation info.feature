@Regression-Edit
@ProviderUser
Feature: Login as a provider user and update organisation data

  Background: login to carehires
    Given User logins to carehires as a provider user

  @UpdateOrganization
  Scenario: Login as a provider user and update organisation data
    And Provider user navigates to Organisation page
#    And Provider user updates provider profile data
#    And Provider user moves to Site and edit data
    And Provider user moves to staff and edit data



    And User moves to Provider - User Management and edit data
    And User moves to Billing Information and edit data
    And User clicks on the Complete Profile button on the Contract Agreement page
    And User navigates to Provider View page
    When User finds recently created provider
    Then User verifies the provider profile status as Profile Complete
    And User clicks on the approve button on provider information page
    And User verifies the provider profile status as Approved