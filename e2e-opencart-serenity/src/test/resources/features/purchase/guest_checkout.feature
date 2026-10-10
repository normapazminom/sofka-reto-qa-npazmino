Feature: Guest checkout in the OpenCart store
  As a shopper
  I want to buy products without creating an account
  So that I can complete my order quickly

  @e2e @guest-checkout
  Scenario: Buy two products as a guest and confirm the order
    Given Norma opens the OpenCart store
    When Norma adds the product "MacBook" to the cart
    And Norma adds the product "iPhone" to the cart
    And Norma views the shopping cart
    Then Norma should see "MacBook" and "iPhone" in the cart
    When Norma starts the checkout as a guest with these details:
      | firstName | Norma                    |
      | lastName  | Tester                   |
      | email     | norma.tester@example.com |
      | telephone | 0991234567               |
      | address   | 123 Test Street          |
      | city      | Los Angeles              |
      | postcode  | 90001                    |
      | country   | United States            |
      | region    | California               |
    And Norma accepts the shipping method and the payment terms
    And Norma confirms the order
    Then Norma should see the message "Your order has been placed!"
