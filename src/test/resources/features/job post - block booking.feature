@Regression
@Job
@SuperAdminUser
Feature: Test CareHires create job post - block booking

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @BlockBookingWithoutWorker
  Scenario: Custom jobs /Without recurrence /Without breaks /With Skills /Without worker /No job notes
    And User enters Job Details without enabling Recurrence and Breaks
    When User enters skills with block booking but no note
    Then User enters Job Summary

  @BlockBookingConvertToOpen
  Scenario: Convert to open - already blocked booking job
    And User enters Job Details without enabling Recurrence and Breaks
    And User enters skills with block booking but no note
    And User enters Job Summary
    When User clicks on Convert to Open
    Then User verifies the availability of not allocated text