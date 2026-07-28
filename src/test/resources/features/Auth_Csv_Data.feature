Feature: Event Hub Authentication API Test Cases

  Scenario Outline: Successfully creating a user using User Registration API
    Given an EventHub API is available
    And I build the request payload from CSV row "<testCaseId>" from file "src/test/resources/testdata/payload_data.csv"
    When I send a POST request to "/register"
    Then the status code should be <code>
    And the response message should be "<message>"
    And a valid JWT token is generated

    Examples:
      | testCaseId | code | message |
      | TC01       | 201  | true    |
      | TC02       | 201  | true    |