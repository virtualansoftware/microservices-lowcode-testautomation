Feature: PostMessage-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-send @json
  Scenario: Create order -  kafka message
    Given as a user perform message Create order action
    When send inline message As a user needs to for event OrderCreated on json with type JSONType
      | {                                                                                                                                                    |
      | "customer": {                                                                                                                                        |
      | "customerId": 1001,                                                                                                                                  |
      | "firstname": "Ronnie",                                                                                                                               |
      | "lastname": "Sander"                                                                                                                                 |
      | },                                                                                                                                                   |
      | "orderNumber" : "1234-1234-1234",                                                                                                                    |
      | "orderDesc": "Order Dell Laptop",                                                                                                                    |
      | "orderStatus": "Started",                                                                                                                            |
      | "purchasedProducts": [                                                                                                                               |
      | {                                                                                                                                                    |
      | "productId": 901,                                                                                                                                    |
      | "productName": "Dell Inspiron 3583 15",                                                                                                              |
      | "productDesc": "Laptop Intel Celeron – 128GB SSD – 4GB DDR4 – 1.6GHz - Intel UHD Graphics 610 - Windows 10 Home in S Mode - Inspiron 15 3000 Series" |
      | }                                                                                                                                                    |
      | ]                                                                                                                                                    |
      | }                                                                                                                                                    |
    And verify contains order information for event OrderCreated contains 1234-1234-1234 on json with type JSONType
      | orderNumber,orderDesc,orderStatus, customer/customerId:firstname:lastname,orderStatus |
      | 1234-1234-1234,Order Dell Laptop,Started,i~1001:Ronnie:Sander,Started                 |
