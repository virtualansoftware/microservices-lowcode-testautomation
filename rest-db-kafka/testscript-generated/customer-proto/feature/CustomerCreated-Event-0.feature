Feature: CustomerCreated-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-post @apiservice
  Scenario: Create Customer - api call
    Given As a user needs to perform create customer api action
    And add the TEXT(RANDBETWEEN(2001,5000),"0") value of the key as customerId
    And add details with given header params
      | contentType | application/json |
    And create customer with given input
      | firstname  | Ronnie       |
      | customerId | [customerId] |
      | lastname   | Sander       |
    When user post application/json in /customer/5 resource on apiservice
    Then the status code is 201
    And verify api response csvson includes in the response
      | customerId,firstname,lastname |
      | i~[customerId],Ronnie,Sander  |
    And verify contains customer information for event CustomerCreated contains [customerId] on proto with type ProtobufType
      | customerId_,firstname_,lastname_ |
      | i~[customerId],Ronnie,Sander     |
