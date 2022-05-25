Feature: SendPetMessage-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-kafka-send @jsonschema
  Scenario: Create Pet -  kafka message
    Given as a user perform message Create Pet action
    When send inline message As a user needs to for event PetCreated on jsonschema with type JsonSchemaType
      | {                      |
      | "photoUrls": [         |
      | "string"               |
      | ],                     |
      | "name": "doggie",      |
      | "id": 100,             |
      | "category": {          |
      | "name": "string",      |
      | "id": 100              |
      | },                     |
      | "status": "available", |
      | "tags": [              |
      | {                      |
      | "name": "string",      |
      | "id": 0                |
      | }                      |
      | ]                      |
      | }                      |
    And verify contains pet information for event PetCreated contains 100 on jsonschema with type JsonSchemaType
      | id,name,status         |
      | i~100,doggie,available |
