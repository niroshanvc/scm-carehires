@Regression
@ProviderUser
Feature: Login as a provider user and update organisation data

  Background: login to carehires
    Given User logins to carehires as a provider user

  @UpdateOrganization
  Scenario: Login as a provider user and update organisation data
    And Provider user navigates to Organisation page
    And Provider user updates provider profile data
    And Provider user moves to Site and edit data
    And Provider user moves to staff and edit data
    And Provider user moves to User Management and edit data
    And Provider user moves to Billing Information and edit data
    And Provider user moves to Restrictions and edit data
    And User logins off from Carehires
    And User logins to carehires
    And User navigates to Provider View page
    And User finds provider name equal to Automation Provider 148428
    When User accepts a worker pending approval
    And User logins off from Carehires
    And User logins to carehires as a provider user
    And Provider user navigates to Organisation page
    Then Provider user verifies added restricted worker displays in the table grid
    And Provider user removes restricted worker
    And Provider user moves to Notification Management and update data
    And Provider user updates organisation settings