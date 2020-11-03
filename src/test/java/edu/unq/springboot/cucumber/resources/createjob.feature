Feature: Create a new Job in the portfolio
  Scenario: A request is received to create a new job
    When Request to create a new job
    Then The new job is created and saved

  Scenario: A request is received to create a new job with a wrong initial date
    When Request to create a new job with invalid date '2010-31-01'
    Then The new job is not saved and an error is produced
