Feature: Find all the jobs of a registered user
  Scenario: A request is received to get the jobs of a user
    When Request to get jobs of user 'user0'
    Then List of 'user0' jobs is received
