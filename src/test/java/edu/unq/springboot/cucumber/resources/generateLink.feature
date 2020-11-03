Feature: Generate a link to the user's portfolio

#  As a user
#  I want to share my portfolio
#  So other people can see them

#  Positive case

  Scenario: A request is received to generate a link to a portfolio
    Given A user without his portfolio link
    When The user request to generate a link to they portfolio
    Then A link should be generated

#  Negative case

  Scenario: A request is received to get a non-created link to a portfolio
    Given A user without his portfolio link
    When An user have not created a link to his portfolio
    Then The user should not have an associated portfolio link
