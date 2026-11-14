package com.framework.api;

import com.framework.api.models.Booking;
import com.framework.base.BaseApiTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CreateBookingTest extends BaseApiTest {

    @Test
    public void createBookingFromPojo() {
        Booking booking = sampleBooking();

        given().spec(spec)
                .body(booking)
                .when().post("/booking")
                .then().statusCode(200)
                .body("bookingid", greaterThan(0))
                .body("booking.firstname", equalTo(booking.getFirstName()))
                .body("booking.lastname", equalTo(booking.getLastName()))
                .body("booking.totalprice", equalTo(booking.getTotalPrice()))
                .body("booking.bookingdates.checkin", equalTo(booking.getBookingDates().getCheckIn()));
    }
}
