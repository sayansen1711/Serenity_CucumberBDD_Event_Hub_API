package com.practice.eventhub.common.stepdefinition;

import com.practice.eventhub.common.model.EventHub;
import com.practice.eventhub.common.util.LoadTestData;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.annotations.Steps;

import java.util.HashMap;

public class AuthStepDefinition {

    @Steps
    private EventHub eventHub;

    @Steps
    private LoadTestData  loadTestData;

    protected HashMap<String, String> payload;

    protected String payLoadBody;

    private RequestSpecification requestSpecification;

    @Given("an EventHub API is available")
    public void anEventHubAPI() {
        //prepare the headers
        requestSpecification = new RequestSpecBuilder()
                .setContentType("application/json")
//                .setAccept("application/json")
                .addHeader("Accept", "application/json")
                .build();
    }

    @And("a payload with fields {string} and {string} is passed")
    public void aPayloadWithFieldsAndIsPassed(String email, String password) {
        loadTestData.preparePayload("email", email);
        loadTestData.preparePayload("password", password);
        payload = loadTestData.getPayload();
    }
    @When("EventHub API is invoked with {string}")
    public void eventhubAPIIsInvokedWith(String endpoint) {
        eventHub.callApi(endpoint, payload, requestSpecification);
    }

    @Then("the API should pass with {int}, {string}")
    public void theRegistrationShouldPassWith(int returnCode, String message) {
        eventHub.validateResponseForEventHub(returnCode, message);
    }

    @And("a valid JWT token is generated")
    public void validateAJWTTokenIsGenerated() {
        eventHub.validateJwtTokenIsGenerated();
    }

    @Then("the API should fail with {int}, {string}")
    public void theRegistrationShouldFailWithReturnCode(int returnCode, String success) {
        eventHub.validateResponseForEventHub(returnCode, success);
    }

    @And("validate an {string} is displayed")
    public void validateAnErrorMessageIsDisplayed(String error) {
        // Write code here that turns the phrase above into concrete actions
        eventHub.validateMessage(error);
    }

    @And("validate details {string} message is {string}")
    public void validateDetailsMessageIs(String field, String message) {
        eventHub.detailsBodyValidation(field, message);
    }

    @And("I build the request payload from CSV row {string} from file {string}")
    public void iBuildTheRequestPayloadFromCSVRowFromFile(String testCaseId, String fileName) {
        payLoadBody=loadTestData.csvToJson("Test_Case_Id",testCaseId, fileName);
    }

    @When("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(String request) {
        // Write code here that turns the phrase above into concrete actions
        eventHub.callApi(request, payLoadBody, requestSpecification);
    }

    @Then("the status code should be {int}")
    public void theStatusCodeShouldBe(int code) {
        // Write code here that turns the phrase above into concrete actions
        eventHub.validateResponseCode(code);
    }

    @And("the response message should be {string}")
    public void theResponseMessageShouldBe(String message) {
        // Write code here that turns the phrase above into concrete actions
        eventHub.validateResponseMessage(message);
    }
}
