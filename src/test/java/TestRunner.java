
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//@ExtendWith(SerenityJUnit5Extension.class)
public class TestRunner {
    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://api.eventhub.rahulshettyacademy.com/api";
        RestAssured.basePath = "/auth";
    }

    @Title("Get User Test")
    @Test
    public void testGetRequest() {
        String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjE4NjI1LCJlbWFpbCI6InNheWFuLnNlbjE3MTFAZ21haWwuY29tIiwiaWF0IjoxNzgyMzMwNjI0LCJleHAiOjE3ODI5MzU0MjR9.dYLDYf8bY-BDd9Zi3R5w1EoIT7w_wP69eHKl4KkYyHU";
        Response response = SerenityRest
                .given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get("/me").prettyPeek()
                .then()
                .statusCode(200).log().all().extract().response();
        response.then().body("success", Matchers.is(true))
                .body("user.userId", Matchers.is(18625));
//        response.prettyPrint();
    }

    @Title("Login using POST method")
    @Test
    public void testLoginPost() {
        TreeMap<String, String> payLoad = new TreeMap<>();
        payLoad.put("email", "sayan.sen1711@gmail.com");
        payLoad.put("password", "ca3Qmeps");
        Response response = SerenityRest
                .given()
                .header("content-type", "application/json")
                .header("Accept", "application/json")
                .body(payLoad).log().all()
                .when()
                .post("/login").prettyPeek()
                .then()
                .statusCode(200)
                .body("success", Matchers.is(true))
                .body("user.email", Matchers.is("sayan.sen1711@gmail.com"))
                .log().all().extract().response();

        String token = response.jsonPath().getString("token");
    }
}
