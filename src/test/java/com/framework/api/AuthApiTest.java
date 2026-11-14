package com.framework.api;

import com.framework.api.models.AuthRequest;
import com.framework.config.ConfigReader;
import com.framework.base.BaseApiTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthApiTest extends BaseApiTest {

    @Test
    public void createTokenWithValidCredentials() {
        given().spec(spec)
                .body(new AuthRequest(ConfigReader.get("api.username"), ConfigReader.get("api.password")))
                .when().post("/auth")
                .then().statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void invalidCredentialsDoNotReturnToken() {
        given().spec(spec)
                .body(new AuthRequest(ConfigReader.get("api.username"), "wrong"))
                .when().post("/auth")
                .then().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}
