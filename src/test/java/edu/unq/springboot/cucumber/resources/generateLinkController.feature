# USER CONTROLLER BDD

Feature: Generate an user's portfolio link

#  As a user
#  I want to share my portfolio
#  So other people can see them

#  Positive scenario

  Scenario: A request is received to generate a portfolio's link
    Given A registered user on the site
    When The user requests to generate a portfolio's link
    Then The response status should be "200"