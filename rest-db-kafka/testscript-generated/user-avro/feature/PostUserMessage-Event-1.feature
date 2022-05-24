Feature: PostUserMessage-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-kafka-send @avro
  Scenario: Create User -  kafka message
    Given as a user perform message Create User action
    When send inline message As a user needs to for event UserCreated on avro with type AvroType
      | {                            |
      | "name": "Rockey",  "age": 44 |
      | }                            |
    And verify contains user information for event UserCreated contains Ryan on avro with type AvroType
      | name,age    |
      | Rockey,i~44 |
