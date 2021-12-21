Feature: Graphql-Example - Workflow
  Feature: Graphql-Example - Workflow

  Scenario: Load initial set of data
    Given Provided all the feature level parameters from file

  @graphql @graphql
  Scenario: Create user with Graphql - api call
    Given a user perform a api action
    And Add the suki value of the key as name
    And Add the 13 value of the key as age
    And add request with given header params
      | contentType | application/json |
    And Create api with given input
      | variables.name |                                                                               |
      | variables.age  | i~0                                                                           |
      | query          | mutation {\n createAuthor(\n name: "[name]",\n age: [age]) {\n id name\n }\n} |
    When a user post application/json in /apis/graphql resource on graphql
    Then the status code is 200
    And Verify api response csvson includes in the response
      | data.createAuthor/name |
      | [name]                 |
    And Verify across response includes following in the response
      | data.createAuthor.name | [name] |
    And Store the data.createAuthor.id value of the key as id

  @graphql @graphql
  Scenario: Create another user with Graphql - api call
    Given a user perform a api action
    And Add the sri value of the key as name2
    And Add the 8 value of the key as age2
    And add request with given header params
      | contentType | application/json |
    And Create api with given input
      | variables.name |                                                                                 |
      | variables.age  | i~0                                                                             |
      | query          | mutation {\n createAuthor(\n name: "[name2]",\n age: [age2]) {\n id name\n }\n} |
    When a user post application/json in /apis/graphql resource on graphql
    Then the status code is 200
    And Verify api response csvson includes in the response
      | data.createAuthor/name |
      | [name2]                |
    And Verify across response includes following in the response
      | data.createAuthor.name | [name2] |
    And Store the data.createAuthor.id value of the key as id2

  @graphql
  Scenario: Read user - using graphql - api call
    Given a user perform a api action
    And add request with given header params
      | contentType | application/json |
    And Create api with given input
      | query | query findAllAuthors {\n findAllAuthors {\n id\n name\n age\n }\n} |
    When a user post application/json in /apis/graphql resource on graphql
    Then the status code is 200
    And Verify data.findAllAuthors response csvson includes in the response
      | id,name,age        |
      | [id],[name], i~13  |
      | [id2],[name2], i~8 |
