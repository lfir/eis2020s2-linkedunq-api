Feature: generate link for share repository
  Scenario: User needs to share repository
    Given User have not link that repository
    When User requests to generate link
    Then a link is generated

  Scenario: user created have not link
    Given User have not link that repository
    Then User cant generate link
