@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create job

  Background: login to carehires
    Given User logins to carehires

  @CreateAgreement
  Scenario: Create a job
    When User navigates to Jobs page
    And User moves to Post Job page
    And User enters Job Details