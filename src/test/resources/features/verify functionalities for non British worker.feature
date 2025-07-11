@Regression
@Worker
@SuperAdminUser
@NonBritishWorkerCreation
Feature: Test CareHires create worker

  Background: login to carehires
    Given User logins to carehires
    When User navigates to Worker Create page

  @VerifyNonBritishHealthCareExternallySponsoredWorker
  Scenario: Create a worker where nationality is non-British & Visa Type is Health and Care & Externally Sponsored
    And User enters valid non-British worker basic information
    And User enters Document and Proof information for non-British worker
    And User enters Education and Training data
    And User enters Emergency Information data
    And User enters Vaccination and Allergy Information data
    And User enters Employment Information data
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    When User finds recently created worker
    Then User verifies the worker profile status as Profile Complete
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User verifies the worker profile status as Submitted For Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved

  @VerifyNonBritishSkilledAgencySponsoredWorker
  Scenario: Create a worker where nationality is non-British & Visa Type is Skilled Worker & Agency Sponsored
    And User enters valid non-British worker with basic information
    And User enters Document and Proof information for non-British worker
    And User enters Education and Training data
    And User enters Emergency Information data
    And User enters Vaccination and Allergy Information data
    And User enters Employment Information data
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    When User finds recently created worker
    Then User verifies the worker profile status as Profile Complete
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User verifies the worker profile status as Submitted For Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved

  @VerifyNonBritishStudentWorker
  Scenario: Create a worker where nationality is non-British & Visa Type is Student
    And User enters valid non-British worker and related basic information
    And User enters Document and Proof information for non-British Student worker
    And User enters Education and Training data
    And User enters Emergency Information data
    And User enters Vaccination and Allergy Information data
    And User enters Employment Information data
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    When User finds recently created worker
    Then User verifies the worker profile status as Profile Complete
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User verifies the worker profile status as Submitted For Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved

  @VerifyNonBritishMaximumWeeklyHoursWorker
  Scenario: Create a worker where nationality is non-British & Visa Type is Graduate Settlement
    And User enters valid non-British worker details by selecting Graduate Settlement as Visa Type
    And User uploads Document and Proof information for non-British worker
    And User enters Education and Training data
    And User enters Emergency Information data
    And User enters Vaccination and Allergy Information data
    And User enters Employment Information data
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    And User finds recently created worker
    And User verifies the worker profile status as Profile Complete
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User verifies the worker profile status as Submitted For Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved
    And User navigates to Jobs page
    And User moves to Post New Job page
    And User enters Job Details information without enabling Recurrence and Breaks
    And User enters skills and block booking but without note
    And User enters Job Summary
    And User suggests a worker for selected job


  @EditNonBritishWorker
  Scenario: Create a draft worker and update visa type and other related information
    And User creates a non-British worker in draft stage
    And User moves to Basic Information page again
    And User collects workerId from Basic Information page
    And User navigates to Worker View page
    And User finds recently created worker
    And User edit non-British worker profile data

