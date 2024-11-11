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
    And User moves to the payment profile page and edit data
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
