Feature: ProductCreated-Event - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @simple-post @apiservice
  Scenario: Create product - api call
    Given As a user needs to perform create product api action
    And add the TEXT(RANDBETWEEN(2001,5000),"0") value of the key as productId
    And add details with given header params
      | contentType | application/json |
    And create product with given input
      | productDesc | Laptop Intel Celeron – 128GB SSD – 4GB DDR4 – 1.6GHz - Intel UHD Graphics 610 - Windows 10 Home in S Mode - Inspiron 15 3000 Series |
      | productId   | [productId]                                                                                                                         |
      | productName | Dell Inspiron 3583 15                                                                                                               |
    When user post application/json in /product/0 resource on apiservice
    Then the status code is 201
    And verify api response csvson includes in the response
      | productId,productName,productDesc                                                                                                                                       |
      | i~[productId],Dell Inspiron 3583 15,Laptop Intel Celeron – 128GB SSD – 4GB DDR4 – 1.6GHz - Intel UHD Graphics 610 - Windows 10 Home in S Mode - Inspiron 15 3000 Series |
    And verify contains product information for event ProductCreated contains [productId] on proto with type ProtobufType
      | productId_,productName_,productDesc_                                                                                                                                    |
      | i~[productId],Dell Inspiron 3583 15,Laptop Intel Celeron – 128GB SSD – 4GB DDR4 – 1.6GHz - Intel UHD Graphics 610 - Windows 10 Home in S Mode - Inspiron 15 3000 Series |
