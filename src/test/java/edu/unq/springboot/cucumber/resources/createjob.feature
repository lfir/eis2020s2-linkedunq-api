Feature: Create a new Job in the portfolio
  Scenario: A request is received to create a new job
    When Request to create a new job
    Then The new job is created and saved
