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
    When Provider User enters Job Preferences
    Then User enters Job Summary

  @ProviderUserPostGeneralJobWithBreaks
  Scenario: Login as a provider user and post a general job with break
    And Provider User enters Job Details with Breaks
    When Provider User enters Job Preferences
    Then User enters Job Summary

  @ProviderUserPostGeneralJobFromTemplate
  Scenario: Login as a provider user and post a general job from a template
    And Provider User selects Post using Template and select the template as Job Post - Automation 010
    And Provider User enters Job Details without Recurrence and Breaks
    When Provider User enters Job Preferences with block booking
    Then User enters Job Summary from template

  @ProviderUserPostSleepInJob
  Scenario: Login as a provider user and post a general job with break
    And Provider User selects job type as Sleep In and proceed with custom job
    When Provider User enters Job Preferences
    Then User enters Job Summary

