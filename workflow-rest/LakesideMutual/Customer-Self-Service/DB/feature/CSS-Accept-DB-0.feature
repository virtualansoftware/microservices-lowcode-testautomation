Feature: CSS-Accept-DB - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @token @okta @store-response @variable @password @sceret @workflow @cssapi
  Scenario: Customer Self-Service Auth - api call
    Given as a insurance user perform login api action
    And add content type with given header params
      | contentType | application/json |
    And create login information with given input
      | password | [password] |
      | email    | [email]    |
    When logging in using post application/json in /auth resource on cssapi
    Then the status code is 200
    And verify user email information includes following in the response
      | email | [email] |
    And store token as key and api's token as value

  @dynamic-date @dynamic-value @cssapi
  Scenario: GetCustomerByLogin - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    When a user get application/json in /user resource on cssapi
    Then the status code is 200
    And verify across response includes following in the response
      | email | [email] |
    And store customerId as key and api's customerId as value
    And evaluate key as expiryDate and SUBSTITUTE(TEXT(NOW()+365, "yyyy-mm-dd HH:mm:ss"), " ", "T") as function value
    And evaluate key as startDate and TEXT(TODAY(),"yyyy-mm-dd") as function value

  @store_response @workflow @create_response_variable @cssapi
  Scenario: GetCustomerInfoByCustomerId - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    When a user get application/json in /customers/[customerId] resource on cssapi
    Then the status code is 200
    And verify across response includes following in the response
      | firstname | Max |
    And store customerId as key and api's customerId as value
    And store firstname as key and api's firstname as value
    And store lastname as key and api's lastname as value
    And store streetAddress as key and api's streetAddress as value
    And store postalCode as key and api's postalCode as value
    And store city as key and api's city as value

  @pass_dynamic_variables @workflow @cssapi
  Scenario: CreateInsuranceQuote - api call
    Given a user perform a api action
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
      | insuranceOptions.insuranceType            | Life Insurance  |
      | insuranceOptions.startDate                | 2021-06-20      |
    When a user post application/json in /insurance-quote-requests resource on cssapi
    Then the status code is 200
    And store quoteId as key and api's id as value

  @workflow @dynamic_date @quoteapi
  Scenario: ReceiveInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And update api with given input
      | insurancePremium.amount   | i~500             |
      | insurancePremium.currency | CHF               |
      | policyLimit.amount        | i~50000           |
      | policyLimit.currency      | CHF               |
      | status                    | QUOTE_RECEIVED    |
      | expirationDate            | [expiryDate].000Z |
    When a user patch application/json in /insurance-quote-requests/[quoteId] resource on quoteapi
    Then the status code is 200
    And verify across response includes following in the response
      | id | [quoteId] |

  @json_Array @json_path @cssapi
  Scenario: AcceptInsuranceQuote - api call
    Given a user perform a api action
    And add request with given header params
      | contentType  | application/json |
      | X-Auth-Token | [token]          |
    And update api with given input
      | status | QUOTE_ACCEPTED |
    When a user patch application/json in /insurance-quote-requests/[quoteId] resource on cssapi
    Then the status code is 200
    And verify api response csvson includes in the response
      | statusHistory/status                                |
      | REQUEST_SUBMITTED\|QUOTE_RECEIVED\|QUOTE_ACCEPTED\| |
    And verify across response includes following in the response
      | id | [quoteId] |

  @sql_select @sql_validate @store_sql_response @cssdb
  Scenario: InsuranceQuoteByDB - database action
    Given as a user perform sql verify record action
    When read details on the given query on cssdb
      | select iqr.id, iq.insurance_premium_amount, iq.insurance_premium_currency, iq.policy_limit_amount from insurancequotes iq INNER JOIN insurancequoterequests iqr on iq.id = iqr.insurance_quote_id and iqr.id  =  [quoteId] |
    Then validate information on the given details on cssdb
      | id,insurance_premium_amount, insurance_premium_currency, policy_limit_amount |
      | i~[quoteId],d~500.00,CHF,d~50000.00                                          |
    And store policy_limit_amount as key and query's [0].policy_limit_amount as value
