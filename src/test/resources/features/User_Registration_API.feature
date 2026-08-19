Feature: Event Hub Authentication API Test Cases

  @RegisterAPI_PositiveTestCase
  Scenario Outline: Successfully creating a user using User Registration API
    Given an EventHub API is available
    And I build the request payload from CSV row "<testCaseId>" from file "src/test/resources/testdata/registration_payload_data.csv"
    When I send a POST request to "/register"
    Then the status code should be 201
    And the response message should be "true"
    And a valid JWT token is generated

    Examples:
      | testCaseId |
      | TC01       |

  @RegisterAPI_NegativeTestCase
  Scenario Outline: Validating Negative Test Cases for User Registration API
    Given an EventHub API is available
    And I build the request payload from CSV row "<testCaseId>" from file "src/test/resources/testdata/registration_payload_data.csv"
    When I send a POST request to "/register"
    Then the status code should be 400
    And the response message should be "false"
    And validate an "<error>" is displayed
    And validate details "<field>" message is "<message>"

    Examples:
      | testCaseId | error                    | field           | message                                                           |
      | TC02       | Email already registered |                 |                                                                   |
      | TC03       | Validation failed        | email           | A valid email is required                                         |
      | TC04       | Validation failed        | password        | Password must be at least 6 characters                            |
      | TC05       | Validation failed        | password        | Password must be at least 6 characters                            |
      | TC06       | Validation failed        | email, password | A valid email is required, Password must be at least 6 characters |
      | TC07       | Validation failed        | email, password | A valid email is required, Password must be at least 6 characters |

