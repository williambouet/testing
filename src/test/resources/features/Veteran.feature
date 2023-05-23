Feature: Veteran feature
    Scenario: This is a second scenario to test another feature
        Given the following firemen:
            | Toto              |   |            |      |
            | Titi              | 1 | littleFire | 2020 |
            | Tata              |   |            |      |
        When I look for oldest fireman
        Then 1 veteran returned and name is "Titi"