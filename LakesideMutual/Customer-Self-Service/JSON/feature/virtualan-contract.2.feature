Feature: Examples
    Scenario: Load initial set of data
      Given Provided all the feature level parameters from file
    @examples @api
    Scenario: Read Customer infos - Example-case1 - GET api call
      Given a user perform a api action
      And add request with given header params
        | contentType                   | application/json                         |
      When a user get application/json in /api/persons/bgates resource on api
      Then Verify the status code is 200
      And Verify api response csvson includes exact-match in the response
        | dateOfBirth,firstName,lastName,lastTimeOnline,spokenLanguages/additionalProp1:additionalProp3:additionalProp2,username |
        | 1955-10-28,Bill,Gates,2020-08-30T20:28:36.267Z,Tamil:Spanish:English,bgates |
    @examples @api
    Scenario: Read Customer infos - Example-case2 - GET api call
      Given a user perform a api action
      And add request with given header params
        | contentType                   | application/json                         |
      When a user get application/json in /api/persons/bgates resource on api
      Then Verify the status code is 200
      And Verify api response csvson includes exact-order-match in the response
        | dateOfBirth,lastName,firstName,lastTimeOnline,spokenLanguages/additionalProp1:additionalProp3:additionalProp2 |
        | 1955-10-28,Gates,Bill,2020-08-30T20:28:36.267Z,Tamil:Spanish:English |
    @examples @api
    Scenario: Read Customer infos - Example-case3 - GET api call
      Given a user perform a api action
      And add request with given header params
        | contentType                   | application/json                         |
      When a user get application/json in /api/persons/bgates resource on api
      Then Verify the status code is 200
      And Verify api response csvson includes in the response
        | dateOfBirth,firstName,lastName,lastTimeOnline,spokenLanguages/additionalProp1:additionalProp2:additionalProp3,username |
        | 1955-10-28,Bill,Gates,2020-08-30T20:28:36.267Z,Tamil:English:Spanish,bgates |
    @examples @quote
    Scenario: Read Customer infos - Exact-Order-Match - GET api call
      Given a user perform a api action
      And add request with given header params
        | contentType                   | application/json                         |
      When a user get application/json in /customers resource on quote
      Then Verify the status code is 200
      And Verify customers response csvson includes exact-order-match in the response
        | customerId,firstname,lastname,postalCode,streetAddress |
        | bunlo9vk5f,Ado,Kinnett,6500,2 Autumn Leaf Lane |
      And Verify across response includes following in the response
        | customers.find { it.firstname == 'Ado' }.postalCode                 |  6500                          |
        | customers.find { it.customerId == 'bunlo9vk5f' }.firstname                 |  Ado                          |
        | customers.find { it.customerId == 'f2m0v9b73c' }.email                 |  blangman14@example.com                          |
    @examples @quote
    Scenario: Read Customer infos - Find last element - GET api call
      Given a user perform a api action
      And add request with given header params
        | contentType                   | application/json                         |
      When a user get application/json in /customers resource on quote
      Then Verify the status code is 200
      And Store the customers[-1].customerId value of the key as id
    @examples @api
    Scenario: RiskFactor-DirtectResponse - POST api call
      Given a user perform a api action
      And add request with given header params
        | contentType                   | application/json                         |
      And Create api with given input
        | birthday                   |  1918-10-24                        |
        | postalCode                   |  60563                        |
      When a user post application/json in /api/riskfactor/compute resource on api
      Then Verify the status code is 200
      And Verify api response with 65 includes in the response
      And Store the . value of the key as riskFactor
      And evaluate the [riskFactor]=65 condition success
