Feature: Multi-Run - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @ep
  Scenario: post API Testing - api call
    Given a user perform a api action
    And add the <petId> value of the key as petId
    And add the <petName> value of the key as petName
    And add request with given header params
      | contentType | application/json |
    And create api with given input
      | photoUrls[0]  | string    |
      | name          | <petName> |
      | id            | <petId>   |
      | category.name | string    |
      | category.id   | i~0       |
      | type          | DOGS      |
      | status        | available |
      | tags[0].name  | string    |
      | tags[0].id    | i~0       |
    When a user post application/json in /pets resource on ep
    Then the status code is 201

    Examples:
      | petName | petId |
      | Test1   | 1000  |
      | Test2   | 2000  |
