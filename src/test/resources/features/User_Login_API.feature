Feature: EventHub API Authentication Login API

  @Login_PositiveTestCase
  Scenario Outline: Successfully log in an existing user with valid credentials

    Given an EventHub API is available
    And I build the request payload from CSV row "<testCaseId>" from file "src/test/resources/testdata/login_payload_data.csv"
    When I send a POST request to "/login"
    Then the status code should be 200
    And the response message should be "true"
    And a valid JWT token is generated

    Examples:
      | testCaseId |
      | TC01       |

  @Login_NegativeTestCase
  Scenario Outline: Fail Login with Incorrect Credentials

    Given an EventHub API is available
    And I build the request payload from CSV row "<testCaseId>" from file "src/test/resources/testdata/login_payload_data.csv"
    When I send a POST request to "/login"
    Then the status code should be 400
    And the response message should be "false"
    And validate an "Validation failed" is displayed
    And validate details "<field>" message is "<message>"

    Examples:
      | testCaseId | field           | message                                                           |
      | TC02       | email           | A valid email is required                                         |
      | TC03       | email           | A valid email is required                                         |
      | TC04       | password        | Password must be at least 6 characters                            |
      | TC05       | password        | Password must be at least 6 characters                            |
      | TC06       | email, password | A valid email is required, Password must be at least 6 characters |
      | TC07       | email, password | A valid email is required, Password must be at least 6 characters |

