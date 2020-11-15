Feature: Modify the title of the portfolio

Scenario: It is requested to modify the title of the portfolio
Given A user with the default title
When User wants to change title
Then The title is modified