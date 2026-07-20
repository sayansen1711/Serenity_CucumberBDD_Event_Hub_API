package com.practice.eventhub.common.util;

import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;

public class Hooks {
    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://api.eventhub.rahulshettyacademy.com/api";
        RestAssured.basePath = "/auth";
    }
}
