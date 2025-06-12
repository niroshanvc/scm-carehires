@Regression
@ProviderUser
Feature: Login as a provider user and verify settings


  @VerifyProviderOverviewLinks
  Scenario: Login as a provider user and verify job template settings
    Given User logins to carehires as a provider user
    When Provider User navigates to Overview page
    Then verifies links are working as expected