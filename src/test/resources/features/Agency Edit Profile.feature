@Regression
@Agency
@SuperAdminUser
Feature: Test CareHires edit agency and make profile approved

  Background: login to carehires
    Given User logins to carehires

  @EditAgencyInDraft
  Scenario: Edit agency details where profile is in draft stage
    When User navigates to Agency Create page
    And User creates a agency in draft stage
    And User moves to Agency - Basic Information page again
    And User collects agencyId from Basic Information page
    And User logins off from Carehires
    And User again logins to carehires
    And User navigates to Agency View page
    And User finds recently created agency

    And User moves to the profile page and edit data
    And User moves to Credit Service and edit data
    And User moves to Locations and edit data
    And User moves to Staff and edit data
    And User moves to Billing and edit data
    And User moves to User Management and edit data
    And User moves to Agreement and completes the profile
    And User downloads the manually signed agreement
    And User moves to the profile page and gets the agency id
    And User navigates to Agency View page
    When User finds recently updated agency
    Then User verifies the agency profile status as Profile Complete
    And User completes the profile approve status
    And User again navigates to the Agency Profile page
    And User verifies the agency profile status as Approved
