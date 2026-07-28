package com.practice.eventhub.common.model;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.rest;
import static net.serenitybdd.rest.SerenityRest.then;

public class EventHub {

    public void callApi(String endpoint, HashMap<String, String> payload, RequestSpecification requestSpecification) {
        System.out.println("EventHub API is invoked with " + endpoint);
        rest()
                .given()
                .spec(requestSpecification)
//                .header("content-type", "application/json")
//                .header("Accept", "application/json")
                .body(payload).log().all()
                .when().post(endpoint);
        then().log().all().extract().response().asString();
    }

    public void validateResponseForEventHub(int returnCode, String returnMessage) {
        Assertions.assertEquals(returnCode, then().extract().response().getStatusCode());
        Assertions.assertEquals(Boolean.parseBoolean(returnMessage),
                then().extract().response().jsonPath().getBoolean("success"));
    }

    public void validateJwtTokenIsGenerated() {
        Assertions.assertNotNull(then().extract().jsonPath().getString("token"), "JWT Token not generated");
        Assertions.assertNotNull(then().extract().jsonPath().getString("user.id"), "User ID not generated");
    }

    public void validateMessage(String expectedMessage) {
        Response response = SerenityRest.lastResponse();
        Assertions.assertEquals(expectedMessage, response.jsonPath().getString("error"), "Error Message Assertion Failed");
    }

    public void detailsBodyValidation(String field, String expectedMessage){
        Response response = SerenityRest.lastResponse();
        List<Map<String, String>> details = response.jsonPath().getList("details");
        if(field.isBlank() && expectedMessage.isBlank()){
           Assertions.assertTrue(details.isEmpty(),"Assertion Failed: Details object is not empty");
        } else if(field.equals("email")){
            Assertions.assertEquals(expectedMessage,response.jsonPath().getString("details[0].message"),"Assertion Failed: Email Field");
        } else if(field.equals("password")){
            Assertions.assertEquals(expectedMessage, response.jsonPath().getString("details[0].message"),"Assertion Failed: Password Field");
        } else{
            String[] fields =field.split(", ");
            String[] messages =expectedMessage.split(", ");
            Assertions.assertEquals(fields.length,messages.length,"Assertion Failed: Invalid Fields Length");
            for(int i=0;i<fields.length;i++){
//                System.out.println(fields[i]+"\t"+messages[i]);
                Assertions.assertEquals(fields[i], details.get(i).get("field"), "Assertion Failed: Invalid Field");
                Assertions.assertEquals(messages[i], details.get(i).get("message"), "Assertion Failed: Message Field");
            }
        }
    }
}
