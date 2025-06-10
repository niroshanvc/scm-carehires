@Regression
@ProviderUser
Feature: Login as a provider user and verify settings


  @ProviderUserCreateJobTemplate
  Scenario: Login as a provider user and verify job template settings
    Given User logins to carehires as a provider user
    When Provider User navigates to Settings
    And Provider User creates a new job template

