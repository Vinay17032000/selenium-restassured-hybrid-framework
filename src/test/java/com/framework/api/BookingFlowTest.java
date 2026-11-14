package com.framework.api;

import com.framework.api.models.Booking;
import com.framework.base.BaseApiTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BookingFlowTest extends BaseApiTest {

    @Test
    public void createFetchAndUpdateBooking() {
        int bookingId = given().spec(spec)
                .body(sampleBooking())
                .post("/booking")
                .then().statusCode(200)
                .extract().path("bookingid");

        Booking fetched = given().spec(spec)
                .get("/booking/{id}", bookingId)
                .then().statusCode(200)
                .extract().as(Booking.class);
        assertEquals(fetched.getFirstName(), "Jim");

        Booking changes = sampleBooking();
        changes.setFirstName("James");
        changes.setTotalPrice(300);

        Booking updated = given().spec(spec)
                .header("Cookie", "token=" + fetchToken())
                .body(changes)
                .put("/booking/{id}", bookingId)
                .then().statusCode(200)
                .extract().as(Booking.class);
        assertEquals(updated.getFirstName(), "James");
        assertEquals(updated.getTotalPrice(), 300);

        Booking reFetched = given().spec(spec)
                .get("/booking/{id}", bookingId)
                .then().statusCode(200)
                .extract().as(Booking.class);
        assertEquals(reFetched.getFirstName(), "James");
        assertEquals(reFetched.getTotalPrice(), 300);
    }
}
