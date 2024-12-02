@Regression
@Worker
@SuperAdminUser
Feature: Test CareHires edit worker and make profile approved

  Background: login to carehires
    Given User logins to carehires

  @EditWorkerProfile
  Scenario: Edit worker details where profile is in draft stage
    When User navigates to Provider Create page
    And User navigates to Agency View page
    And User navigates to Provider View page
    And User navigates to Worker View page
    And User navigates to Agency Create page
    And User navigates to Worker Create page