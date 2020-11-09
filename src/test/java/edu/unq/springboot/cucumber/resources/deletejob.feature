Feature: Delete a job from the user's portfolio

#  User Story

#  As a user
#  I want to delete a job from my portfolio
#  So i can keep it updated

  Scenario: A request is received to delete a user's job
    Given A user's job
    When A user request to delete a job
    Then The response status should be 200
