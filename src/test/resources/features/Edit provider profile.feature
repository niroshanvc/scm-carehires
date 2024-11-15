@Regression
@Provider
@SuperAdminUser
Feature: Test CareHires edit provider and make profile approved

  Background: login to carehires
    Given User logins to carehires

  @EditDraftProvider
  Scenario: Edit provider details where profile is in draft stage
    When User navigates to Provider Create page
    And User creates a provider in draft stage
    And User logins off from Carehires
    And User logins to carehires
    And User navigates to Provider View page
    And User searches previously created provider which is in Draft stage
    And User moves to Site and edit data
    And User moves to Worker Staff and edit data
    And User moves to Provider - User Management and edit data
    And User moves to Billing Information and edit data