Feature: Create a new Job in the portfolio

#  User story

#  As a user
#  I want to add a job on my portfolio
#  So that i can update it

#  Positive scenario

  Scenario: A request is received to create a new job
    Given A user's job
    When The user request to create a new job
    Then The new job is created and saved

  Scenario: A request is received to create a new job with a wrong initial date
    When Request to create a new job with invalid date '2010-31-01'
    Then The new job is not saved and an error is produced
