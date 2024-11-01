@Regression
@Provider
@SuperAdminUser
Feature: Test CareHires edit agency and make profile approved

  Background: login to carehires
    Given User logins to carehires

  @EditNonApprovedAgency
  Scenario: Edit agency details where profile is not approved
    When User navigates to Agency View page
    And User searching an agency which is in Draft stage
    And User moves to the profile page and edit data

