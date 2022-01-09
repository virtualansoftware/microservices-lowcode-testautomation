Feature: Example-AttachFile - Workflow

  Scenario: Load initial set of data
    Given Provided all the feature level parameters from file

  @idai
  Scenario: Attach file with multipart - api call
    Given a user perform a api action
    And add request with given header params
      | contentType | multipart/form-data |
      | Accept      | */*                 |
    And add request with multipart/form-data given multipart-form params
      | filestream | sample.json                        |
      | serverurl  | https://live.virtualandemo.com/api |
      | dataload   | APITEST.json                       |
      | execute    | true                               |
      | type       | VIRTUALAN                          |
    When a user post multipart/form-data in /test resource on idai
    Then the status code is 201
    And Verify across response includes following in the response
      | testExecuted | true |
