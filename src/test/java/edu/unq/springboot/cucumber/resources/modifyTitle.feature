Feature: Modify the title of the portfolio

Scenario: It is requested to modify the title of the portfolio
  Given A user with the default title
  When User wants to change title
  Then The title is modified

Scenario: The title of a portfolio is requested
  Given A username
  When  you want to get the title
  Then The title is obtained