package com.framework.base;

import com.framework.api.models.AuthRequest;
import com.framework.api.models.Booking;
import com.framework.api.models.BookingDates;
import com.framework.config.ConfigReader;
import com.framework.reporting.ExtentManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    protected RequestSpecification spec;

    @BeforeClass(alwaysRun = true)
    public void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("api.base.url"))
                .setContentType(ContentType.JSON)
                .setAccept("application/json")
                .addFilter((request, response, context) -> {
                    Response result = context.next(request, response);
                    ExtentManager.log(request.getMethod() + " " + request.getURI() + " -> "
                            + result.getStatusCode() + " (" + result.getTime() + " ms)");
                    return result;
                })
                .build();
    }

    protected String fetchToken() {
        return given().spec(spec)
                .body(new AuthRequest(ConfigReader.get("api.username"), ConfigReader.get("api.password")))
                .post("/auth")
                .then().statusCode(200)
                .extract().path("token");
    }

    protected Booking sampleBooking() {
        return new Booking("Jim", "Brown", 150, true,
                new BookingDates("2025-01-01", "2025-01-05"), "Breakfast");
    }
}
