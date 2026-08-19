Feature: Fetch user details after authentication
  As a registered user
  I want to log in and retrieve my profile details
  So that I can access my account securely

  Scenario: Successfully fetch user profile after a successful login
    Given an EventHub API is available
    And I build the request payload from CSV row "TC01" from file "src/test/resources/testdata/login_payload_data.csv"
    When I send a POST request to "/login"
    Then the status code should be 200
    And a valid JWT token is generated
    When I send a GET request to "/me" using the obtained JWT token
    Then the status code should be 200
    And the response body should contain the user's id and email