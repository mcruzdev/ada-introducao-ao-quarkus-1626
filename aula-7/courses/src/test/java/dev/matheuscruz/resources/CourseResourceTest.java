package dev.matheuscruz.resources;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

class CourseResourceTest {


    @Test
    void should_create_a_course() {

        RestAssured.given()
                .header("Authorization", "Basic ")
                .post();
    }

}