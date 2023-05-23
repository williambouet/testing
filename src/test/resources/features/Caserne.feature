Feature: Caserne feature
    Scenario: This is a first scenario to test a feature
        Given the following fires:
            | Corse             | 2009 |
            | Landes            | 1949 |
            | Charente-Maritime | 1976 |
            | Massif des Maures | 2003 |
        When I look for oldest fire
        Then 1 fire returned and name is "Landes"