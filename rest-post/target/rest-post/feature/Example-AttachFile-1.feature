Feature: Example-AttachFile - Workflow
  Feature: Example-AttachFile - Workflow

  Scenario: Load initial set of data
    Given Provided all the feature level parameters from file

  @idai
  Scenario: Attach file with multipart - api call
    Given a user perform a api action
    And add request with given header params
      | contentType | multipart/form-data |
      | Accept      | */*                 |
    When a user post multipart/form-data in /test resource on idai
    Then the status code is 201
    And Verify across response includes following in the response
      | testExecuted | true |
