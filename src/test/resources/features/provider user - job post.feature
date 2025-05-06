@Regression-Edit
@Job
@ProviderUser
Feature: Login as a provider user and post a job

  Background: login to carehires
    Given User logins to carehires as a provider user
    And User navigates to Jobs page
    And User moves to Post New Job page


  @ProviderUserPostGeneralJob
  Scenario: Login as a provider user and post a general job
    And Provider User enters Job Details
    And User enters Job Preferences
    When User enters Job Summary
    And User writes the job id into a text file