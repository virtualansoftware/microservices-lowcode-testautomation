Feature: Customer Self Service-Accept - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @elan @IDAITHALAM-8 @css
  Scenario: Customer Self-Service Auth - api call
    Given a user perform a api action
    And add request with given header params
      | contentType | application/json |
    And create api with given input
      | password | [password] |
      | email    | [email]    |
    When a user post application/json in /auth resource on css
    Then the status code is 200
    And verify across response includes following in the response
      | email | [email] |
    And store the token value of the key as token

  @css
  Scenario: GetCustomerByLogin - api call
    Given a user perform a api action
    And add the 500 value of the key as insurancePremiumAmount
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    When a user get application/json in /user resource on css
    Then the status code is 200
    And verify across response includes following in the response
      | email | [email] |
    And store the customerId value of the key as customerId
    And evaluate the SUBSTITUTE(TEXT(NOW()+365, "yyyy-mm-dd HH:mm:ss"), " ", "T") function value of the key as expiryDate
    And evaluate the TEXT(TODAY(),"yyyy-mm-dd") function value of the key as startDate

  @css
  Scenario: GetCustomerInfoByCustomerId - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    When a user get application/json in /customers/[customerId] resource on css
    Then the status code is 200
    And verify across response includes following in the response
      | firstname | Max |
    And store the customerId value of the key as customerId
    And store the firstname value of the key as firstname
    And store the lastname value of the key as lastname
    And store the streetAddress value of the key as streetAddress
    And store the postalCode value of the key as postalCode
    And store the city value of the key as city
    And evaluate the LEN("[firstname]")=3 condition success

  @css
  Scenario: CreateInsuranceQuote - api call
    Given a user perform a api action
    And add the Life Insurance value of the key as insuranceType
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And create api with given input
      | customerInfo.firstname                    | [firstname]     |
      | customerInfo.customerId                   | [customerId]    |
      | customerInfo.contactAddress.streetAddress | [streetAddress] |
      | customerInfo.contactAddress.city          | [city]          |
      | customerInfo.contactAddress.postalCode    | [postalCode]    |
      | customerInfo.billingAddress.streetAddress | [streetAddress] |
      | customerInfo.billingAddress.city          | [city]          |
      | customerInfo.billingAddress.postalCode    | [postalCode]    |
      | customerInfo.lastname                     | [lastname]      |
      | insuranceOptions.deductible.amount        | i~500           |
      | insuranceOptions.deductible.currency      | CHF             |
      | insuranceOptions.insuranceType            | [insuranceType] |
      | insuranceOptions.startDate                | [startDate]     |
    When a user post application/json in /insurance-quote-requests resource on css
    Then the status code is 200
    And store the id value of the key as quoteId

  @quote
  Scenario: ReceiveInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And update api with given input
      | insurancePremium.amount   | [insurancePremiumAmount] |
      | insurancePremium.currency | CHF                      |
      | policyLimit.amount        | i~50000                  |
      | policyLimit.currency      | CHF                      |
      | status                    | QUOTE_RECEIVED           |
      | expirationDate            | [expiryDate].000Z        |
    When a user patch application/json in /insurance-quote-requests/[quoteId] resource on quote
    Then the status code is 200
    And verify across response includes following in the response
      | id | [quoteId] |

  @css
  Scenario: AcceptInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And update api with given input
      | status | QUOTE_ACCEPTED |
    When a user patch application/json in /insurance-quote-requests/[quoteId] resource on css
    Then the status code is 200
    And verify api response csvson includes in the response
      | statusHistory/status                                |
      | REQUEST_SUBMITTED\|QUOTE_RECEIVED\|QUOTE_ACCEPTED\| |
    And verify across response includes following in the response
      | id | [quoteId] |
