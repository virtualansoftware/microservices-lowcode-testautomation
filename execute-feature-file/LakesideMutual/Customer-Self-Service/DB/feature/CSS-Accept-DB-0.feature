Feature: CSS-Accept-DB - Workflow

  Scenario: Load initial set of data
    Given Provided all the feature level parameters from file

  @css
  Scenario: Customer Self-Service Auth - api call
    Given a user perform a api action
    And add request with given header params
      | contentType | application/json |
    And Create api with given input
      | password | [password] |
      | email    | [email]    |
    When a user post application/json in /auth resource on css
    Then the status code is 200
    And Verify across response includes following in the response
      | email | [email] |
    And Store the token value of the key as token

  @css
  Scenario: GetCustomerByLogin - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    When a user get application/json in /user resource on css
    Then the status code is 200
    And Verify across response includes following in the response
      | email | [email] |
    And Store the customerId value of the key as customerId
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
    And Verify across response includes following in the response
      | firstname | Max |
    And Store the customerId value of the key as customerId
    And Store the firstname value of the key as firstname
    And Store the lastname value of the key as lastname
    And Store the streetAddress value of the key as streetAddress
    And Store the postalCode value of the key as postalCode
    And Store the city value of the key as city

  @css
  Scenario: CreateInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And Create api with given input
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
      | insuranceOptions.insuranceType            | Life Insurance  |
      | insuranceOptions.startDate                | 2021-06-20      |
    When a user post application/json in /insurance-quote-requests resource on css
    Then the status code is 200
    And Store the id value of the key as quoteId

  @quote
  Scenario: ReceiveInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And Update api with given input
      | insurancePremium.amount   | i~500             |
      | insurancePremium.currency | CHF               |
      | policyLimit.amount        | i~50000           |
      | policyLimit.currency      | CHF               |
      | status                    | QUOTE_RECEIVED    |
      | expirationDate            | [expiryDate].000Z |
    When a user patch application/json in /insurance-quote-requests/[quoteId] resource on quote
    Then the status code is 200
    And Verify across response includes following in the response
      | id | [quoteId] |

  @css
  Scenario: AcceptInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And Update api with given input
      | status | QUOTE_ACCEPTED |
    When a user patch application/json in /insurance-quote-requests/[quoteId] resource on css
    Then the status code is 200
    And Verify api response csvson includes in the response
      | statusHistory/status                                |
      | REQUEST_SUBMITTED\|QUOTE_RECEIVED\|QUOTE_ACCEPTED\| |
    And Verify across response includes following in the response
      | id | [quoteId] |

  @css
  Scenario: InsuranceQuoteByDB - database action
    Given As a user perform sql verify record action
    When Select details with the given sql verify record on css
      | select iqr.id, iq.insurance_premium_amount, iq.insurance_premium_currency, iq.policy_limit_amount from insurancequotes iq INNER JOIN insurancequoterequests iqr on iq.id = iqr.insurance_quote_id and iqr.id  =  [quoteId] |
    Then Verify details with the given sql verify record on css
      | select iqr.id, iq.insurance_premium_amount, iq.insurance_premium_currency, iq.policy_limit_amount from insurancequotes iq INNER JOIN insurancequoterequests iqr on iq.id = iqr.insurance_quote_id and iqr.id  =  [quoteId] |
      | id,insurance_premium_amount, insurance_premium_currency, policy_limit_amount                                                                                                                                               |
      | i~[quoteId],d~500.00,CHF,d~50000.00                                                                                                                                                                                        |
    And Store-sql's [0].policy_limit_amount value of the key as policy_limit_amount
