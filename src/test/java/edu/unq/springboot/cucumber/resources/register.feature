Feature: Register a new User on the site

#  User story

#  As a user
#  I want to register on the page
#  So that i can be able to log in to the site

#  Positive scenario

  Scenario: A request is received to register a new user
    Given A user with valid data
    When The user requests to register on the site
    Then The response status should be "200"

  Scenario: A request is received to register a new recruiter
    Given A recruiter with valid data
    When The recruiter requests to register on the site
    Then The response status should be "200"
