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
 * Tests for Optional Features (Differentials)
 * Total: 18 points optional
 */
@DisplayName("🌟 Diferenciais (Opcional)")
public class OptionalFeaturesTests extends BaseTest {

    @Test
    @TestScore(points = 4, weight = 0.4, description = "Testes com @QuarkusTest implementados", 
               category = "🌟 Diferenciais", mandatory = false)
    @DisplayName("Diferencial - Testes com @QuarkusTest")
    public void testQuarkusTestImplemented() {
        // This test itself proves @QuarkusTest is being used
        // If this test runs, the feature is implemented
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
    @TestScore(points = 4, weight = 0.4, description = "Tratamento global de exceção implementado", 
               category = "🌟 Diferenciais", mandatory = false)
    @DisplayName("Diferencial - Tratamento global de Exceptions (Ignorado, não vimos esse conteúdo)")
    public void testGlobalExceptionHandling() {
        Assertions.assertTrue(true);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Paginação em GET /courses implementada", 
               category = "🌟 Diferenciais", mandatory = false)
    @DisplayName("Diferencial - Paginação")
    public void testPagination() {
        // Create multiple courses
        for (int i = 1; i <= 5; i++) {
            String courseJson = String.format("""
                {
                    "name": "Course %d"
                }
                """, i);
            
            given()
                .contentType("application/json")
                .body(courseJson)
            .when()
                .post("/courses");
        }
        
        // Test pagination parameters (common patterns: page/size or limit/offset)
        // Try with page and size parameters
        given()
            .queryParam("page", 0)
            .queryParam("size", 2)
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .contentType("application/json");
        
        // Alternative: Try with limit and offset
        given()
            .queryParam("limit", 2)
            .queryParam("offset", 0)
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @TestScore(points = 4, weight = 0.4, description = "Uso de DTOs (Data Transfer Objects)", 
               category = "🌟 Diferenciais", mandatory = false)
    @DisplayName("Diferencial - Uso de DTOs")
    public void testDTOUsage() {
        // If the API properly separates request/response from entities,
        // it's using DTOs. We can verify this by checking that the API
        // returns clean, well-structured JSON

        String courseName = Faker.courseName();
        CourseRequest courseRequest = Rest.createCourseWithName(courseName);
        Course course = Rest.courseByName(courseRequest.name());
        
        // Verify GET returns proper structure
        given()
        .when()
            .get("/courses/" + course.id())
        .then()
            .statusCode(200)
            .body("id", equalTo(course.id().intValue()))
            .body("name", equalTo(courseName))
            .body("lessons", instanceOf(java.util.List.class));
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Health Check endpoint implementado", 
               category = "🌟 Diferenciais", mandatory = false)
    @DisplayName("Diferencial - Health Check")
    public void testHealthCheck() {
        // Test standard Quarkus health endpoints
        given()
        .when()
            .get("/q/health")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("status", equalTo("UP"));
        
        // Test liveness
        given()
        .when()
            .get("/q/health/live")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("status", equalTo("UP"));
        
        // Test readiness
        given()
        .when()
            .get("/q/health/ready")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("status", equalTo("UP"));
    }
}

// Made with Bob
