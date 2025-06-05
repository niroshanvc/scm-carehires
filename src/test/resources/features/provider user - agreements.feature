@Regression
@ProviderUser
Feature: Login as a provider user and verify agreements


  @ProviderUserVerifyAgreements
  Scenario: Login as a provider user and verify agreements
    Given User logins to carehires as a provider user
    When Provider User navigates to Agreement View page
    Then User verifies name of the agreement
    And User verifies agreement ids
    And User verifies saved worker rates can be viewed
    And User verifies saved sleep in rates can be viewed
