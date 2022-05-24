Feature: UserCreated-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-post @user
  Scenario: Create User - api call
    Given As a user needs to perform create user api action
    And add details with given query params
      | name | Ryan |
      | age  | 44   |
    And add user with given header params
      | contentType | application/json |
    When user post application/json in /user/publish resource on user
    Then the status code is 200
    And verify contains user information for event UserCreated contains Ryan on avro with type AvroType
      | name,age  |
      | Ryan,i~44 |
