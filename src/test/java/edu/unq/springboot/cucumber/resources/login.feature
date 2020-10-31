Feature: Login on the site

#  User story

#  As a user
#  I want to log in to the application
#  So that i can be able to manage the portfolio

#  Positive scenario

  Scenario: A request is received to login as user
    Given A user with valid data
    When The user login on the site
    Then The response status should be "200"