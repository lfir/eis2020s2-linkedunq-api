Feature: Find all the jobs of a registered user
  Scenario: A request is received to get the jobs of a user
    When Request to get jobs of user 'user0'
    Then List of 'user0' jobs is received

  Scenario: A request is received to get the jobs of a user ordered by priority
    When The user request to retrieve they jobs ordered by priority
    Then The received jobs should be ordered by priority

  Scenario: A request is received to get the jobs of a user ordered by date
    When The user request to retrieve they jobs ordered by date
    Then The received jobs should be ordered by date