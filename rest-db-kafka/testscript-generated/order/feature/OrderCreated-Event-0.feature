Feature: OrderCreated-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-post @orderservice
  Scenario: Create order - api call
    Given As a user needs to perform create order api action
    And add details with given header params
      | contentType | application/json |
    And create order with given input
      | purchasedProducts[0].productDesc | Laptop Intel Celeron – 128GB SSD – 4GB DDR4 – 1.6GHz - Intel UHD Graphics 610 - Windows 10 Home in S Mode - Inspiron 15 3000 Series |
      | purchasedProducts[0].productId   | i~901                                                                                                                               |
      | purchasedProducts[0].productName | Dell Inspiron 3583 15                                                                                                               |
      | orderStatus                      | Started                                                                                                                             |
      | orderDesc                        | Order Dell Laptop                                                                                                                   |
      | customer.firstname               | Ronnie                                                                                                                              |
      | customer.customerId              | i~1001                                                                                                                              |
      | customer.lastname                | Sander                                                                                                                              |
    When user post application/json in /order/5 resource on orderservice
    Then the status code is 201
    And verify api response csvson includes in the response
      | orderDesc,orderStatus, customer/customerId:firstname:lastname,orderStatus |
      | Order Dell Laptop,Started,i~1001:Ronnie:Sander,Started                    |
    And store orderId as key and api's orderNumber as value
    And verify contains order information for event OrderCreated contains [orderId] on json with type JSONType
      | orderNumber,orderDesc,orderStatus, customer/customerId:firstname:lastname,orderStatus |
      | [orderId],Order Dell Laptop,Started,i~1001:Ronnie:Sander,Started                      |
