package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for REST Endpoints
 * Total: 15 points mandatory + 6 points optional
 */
@DisplayName("📡 Endpoints REST")
public class EndpointsTests extends BaseTest {

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint POST /courses existe e funciona", category = "📡 Endpoints")
    @DisplayName("Endpoint - POST /courses")
    public void testPostCoursesEndpoint() {
        String courseJson = """
            {
                "name": "Test Course"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint GET /courses existe e funciona", category = "📡 Endpoints")
    @DisplayName("Endpoint - GET /courses")
    public void testGetCoursesEndpoint() {
        given()
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint GET /courses/{id} existe e funciona", category = "📡 Endpoints")
    @DisplayName("Endpoint - GET /courses/{id}")
    public void testGetCourseByIdEndpoint() {
        // Create a course first
        String courseJson = """
            {
                "name": "Test Course"
            }
            """;
        
        Integer courseId = given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .extract().path("id");
        
        // Test the endpoint
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint PUT /courses/{id} existe e funciona", category = "📡 Endpoints")
    @DisplayName("Endpoint - PUT /courses/{id}")
    public void testPutCourseEndpoint() {
        // Create a course first
        String courseJson = """
            {
                "name": "Original Name"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201);
        
        // Test the endpoint
        String updatedJson = """
            {
                "name": "Updated Name"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(updatedJson)
        .when()
            .put("/courses/" + 1)
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint DELETE /courses/{id} existe e funciona", category = "📡 Endpoints")
    @DisplayName("Endpoint - DELETE /courses/{id}")
    public void testDeleteCourseEndpoint() {
        // Create a course first
        String courseJson = """
            {
                "name": "Course to Delete"
            }
            """;
        
        Integer courseId = given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .extract().path("id");
        
        // Test the endpoint
        given()
        .when()
            .delete("/courses/" + courseId)
        .then()
            .statusCode(204);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint POST /courses/{courseId}/lessons existe e funciona", 
               category = "📡 Endpoints", mandatory = false)
    @DisplayName("Endpoint - POST /courses/{courseId}/lessons (PLUS)")
    public void testPostLessonEndpoint() {
        // Create a course first
        String courseJson = """
            {
                "name": "Course with Lessons"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201);
        
        // Create a lesson
        String lessonJson = """
            {
                "name": "First Lesson"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(lessonJson)
        .when()
            .post("/courses/" + 1 + "/lessons")
        .then()
            .statusCode(201)
            .contentType("application/json");
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint GET /courses/{courseId}/lessons existe e funciona", 
               category = "📡 Endpoints", mandatory = false)
    @DisplayName("Endpoint - GET /courses/{courseId}/lessons (PLUS)")
    public void testGetLessonsEndpoint() {
        // Create a course first
        String courseJson = """
            {
                "name": "Course with Lessons"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body("""
                        { "name": "Quarkus Intro" }
                        """)
                        .post("/courses/1/lessons")
                                .then()
                                        .statusCode(201);
        
        // Test the endpoint
        given()
        .when()
            .get("/courses/" + 1 + "/lessons")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("$", instanceOf(java.util.List.class));
    }
}

// Made with Bob
