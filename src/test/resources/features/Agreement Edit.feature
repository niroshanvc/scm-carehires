@Regression-Edit
@Agreement
@SuperAdminUser
Feature: Test CareHires edit agreement with disable Bid4Care

  Background: login to carehires
    Given User logins to carehires

  @CreateAgreement
  Scenario: Edit an agreement
    When User navigates to Agreement View page
