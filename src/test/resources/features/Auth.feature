Feature: Authentication Test Cases

  @PositiveTestCase
  Scenario Outline: Validating positive test cases for User Registration API
    Given an EventHub API is available
    When a payload with fields "<email>" and "<password>" is passed
    And EventHub API is invoked with "<endpoint>"
    Then the API should pass with <returnCode>, "<message>"
    And a valid JWT token is generated

    Examples:
      | endpoint | email           | password | returnCode | message |
      | register | joseph1@gmail.com | ca3Qmeps | 201        | true    |
      | register | joseph2@gmail.com | ca3Qmeps | 201        | true    |

  @NegativeTestCase
  Scenario Outline: Validate negative test cases for User Registration API
    Given an EventHub API is available
    When a payload with fields "<email>" and "<password>" is passed
    And EventHub API is invoked with "<endpoint>"
    Then the API should fail with <returnCode>, "<success>"
    And validate an "<error>" is displayed
    And validate details "<field>" message is "<message>"

    Examples:
      | endpoint | email              | password | returnCode | success | error                    | field           | message                                                           |
      | register | john1401@gmail.com | ca3Qmeps | 400        | false   | Email already registered |                 |                                                                   |
      | register | john151            | ca3Qmeps | 400        | false   | Validation failed        | email           | A valid email is required                                         |
      | register | john1801@gmail.com |          | 400        | false   | Validation failed        | password        | Password must be at least 6 characters                            |
      | register | john1801@gmail.com | passw    | 400        | false   | Validation failed        | password        | Password must be at least 6 characters                            |
      | register | john1801           | passw    | 400        | false   | Validation failed        | email, password | A valid email is required, Password must be at least 6 characters |
      | register |                    |          | 400        | false   | Validation failed        | email, password | A valid email is required, Password must be at least 6 characters |

  @LoginTestCase
  Scenario Outline: Validate the positive cases for User Login API
    Given an EventHub API is available
    When a payload with fields "<email>" and "<password>" is passed
    And EventHub API is invoked with "<endpoint>"
    Then the API should pass with <returnCode>, "<message>"
    And a valid JWT token is generated

    Examples:
      | endpoint | email               | password  | returnCode | message |
      | login    | student@example.com | secret123 | 200        | true    |

  @LoginNegativeTestCase
  Scenario Outline: Validate the negative cases for User Login API
    Given an EventHub API is available
    When a payload with fields "<email>" and "<password>" is passed
    And EventHub API is invoked with "<endpoint>"
    Then the API should fail with <returnCode>, "<success>"
    And validate an "<error>" is displayed
    And validate details "<field>" message is "<message>"

    Examples:
      | endpoint | email               | password   | returnCode | success | error                     | field           | message                                                           |
      | login    | student@example.com | secret1234 | 400        | false   | Invalid email or password |                 |                                                                   |
      | login    | student             | secret123  | 400        | false   | Validation failed         | email           | A valid email is required                                         |
      | login    | student@example.com |            | 400        | false   | Validation failed         | password        | Password must be at least 6 characters                            |
      | login    | student@example.com | passw      | 400        | false   | Validation failed         | password        | Password must be at least 6 characters                            |
      | login    | student             | passw      | 400        | false   | Validation failed         | email, password | A valid email is required, Password must be at least 6 characters |
      | login    |                     |            | 400        | false   | Validation failed         | email, password | A valid email is required, Password must be at least 6 characters |