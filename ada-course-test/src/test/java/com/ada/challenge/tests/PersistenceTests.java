package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import com.ada.challenge.tests.http.Course;
import com.ada.challenge.tests.http.CourseRequest;
import com.ada.challenge.tests.http.Faker;
import com.ada.challenge.tests.http.Rest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Persistence
 * Total: 10 points
 */
@DisplayName("🗂️ Persistência")
public class PersistenceTests extends BaseTest {

    @Test
    @TestScore(points = 10, weight = 1.0, description = "Dados são persistidos (H2 ou PostgreSQL/MySQL)", 
               category = "🗂️ Persistência")
    @DisplayName("Persistência - Dados são salvos e recuperados")
    public void testDataPersistence() {
        // Create a course

        CourseRequest courseRequest = Rest.createCourse();
        Course course = Rest.courseByName(courseRequest.name());

        // Verify the course can be retrieved (data is persisted)
        Assertions.assertNotNull(course.id());
        Assertions.assertNotNull(course.name());
        Assertions.assertEquals(courseRequest.name(), course.name());

        String courseName = Faker.courseName();
        CourseRequest updateCourseRequest = new CourseRequest(courseName);

        // PUT
        given()
            .contentType("application/json")
            .body(updateCourseRequest)
            .put("/courses/" + course.id())
        .then()
            .statusCode(200)
                    .log();

        // GET by ID
        given()
        .when()
            .get("/courses/" + course.id())
        .then()
            .statusCode(200)
            .body("name", equalTo(updateCourseRequest.name()))
                    .log();

        // DELETE
        given()
        .when()
            .delete("/courses/" + course.id())
        .then()
            .statusCode(204);
        
        // GET with 404
        given()
        .when()
            .get("/courses/" + course.id())
        .then()
            .statusCode(404);
    }
}

// Made with Bob
