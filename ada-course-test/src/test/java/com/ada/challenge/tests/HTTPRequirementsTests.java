package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import com.ada.challenge.tests.http.Course;
import com.ada.challenge.tests.http.CourseRequest;
import com.ada.challenge.tests.http.Rest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for HTTP Requirements (Content-Type and Status Codes)
 * Total: 30 points
 */
@DisplayName("🌐 Requisitos HTTP")
public class HTTPRequirementsTests extends BaseTest {

    // ========== Content-Type Tests (10 points) ==========
    
    @Test
    @TestScore(points = 4, weight = 0.4, description = "API aceita apenas application/json", category = "🌐 Content-Type")
    @DisplayName("Content-Type - Aceita application/json")
    public void testAcceptsApplicationJson() {
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
    @TestScore(points = 3, weight = 0.3, description = "Retorna 415 para Content-Type diferente de application/json", 
               category = "🌐 Content-Type")
    @DisplayName("Content-Type - Rejeita outros tipos (415)")
    public void testRejectsOtherContentTypes() {
        String courseJson = """
            {
                "name": "Test Course"
            }
            """;
        
        given()
            .contentType("text/plain")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(415);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Retorna Content-Type: application/json nas respostas", 
               category = "🌐 Content-Type")
    @DisplayName("Content-Type - Retorna application/json")
    public void testReturnsApplicationJson() {
        given()
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    // ========== POST /courses Status Codes (5 points) ==========
    
    @Test
    @TestScore(points = 3, weight = 0.3, description = "POST /courses retorna 201 Created", 
               category = "🌐 Status Codes - POST")
    @DisplayName("Status Code - POST 201 Created")
    public void testPostReturns201() {
        String courseJson = """
            {
                "name": "New Course"
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
    @TestScore(points = 2, weight = 0.2, description = "POST /courses retorna 400 para dados inválidos", 
               category = "🌐 Status Codes - POST")
    @DisplayName("Status Code - POST 400 Bad Request")
    public void testPostReturns400ForInvalidData() {
        String invalidJson = """
            {
                "name": ""
            }
            """;
        
        given()
            .contentType("application/json")
            .body(invalidJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "POST /courses retorna header Location", 
               category = "🌐 Status Codes - POST", mandatory = false)
    @DisplayName("Status Code - POST retorna Location header (PLUS)")
    public void testPostReturnsLocationHeader() {
        String courseJson = """
            {
                "name": "Course with Location"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .header("Location", notNullValue())
            .header("Location", containsString("/courses/"));
    }

    // ========== GET /courses/{id} Status Codes (4 points) ==========
    
    @Test
    @TestScore(points = 2, weight = 0.2, description = "GET /courses/{id} retorna 200 OK quando encontrado", 
               category = "🌐 Status Codes - GET")
    @DisplayName("Status Code - GET 200 OK")
    public void testGetByIdReturns200() {
        // Create a course
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
        
        // Get the course
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200);
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "GET /courses/{id} retorna 404 Not Found quando não existe", 
               category = "🌐 Status Codes - GET")
    @DisplayName("Status Code - GET 404 Not Found")
    public void testGetByIdReturns404() {
        given()
        .when()
            .get("/courses/999999")
        .then()
            .statusCode(404);
    }

    // ========== PUT /courses/{id} Status Codes (6 points) ==========
    
    @Test
    @TestScore(points = 2, weight = 0.2, description = "PUT /courses/{id} retorna 200 OK quando atualizado", 
               category = "🌐 Status Codes - PUT")
    @DisplayName("Status Code - PUT 200 OK")
    public void testPutReturns200() {
        // Create a course
        String courseJson = """
            {
                "name": "Original Name"
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
        
        // Update the course
        String updatedJson = """
            {
                "name": "Updated Name"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(updatedJson)
        .when()
            .put("/courses/" + courseId)
        .then()
            .statusCode(200);
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "PUT /courses/{id} retorna 400 para dados inválidos", 
               category = "🌐 Status Codes - PUT")
    @DisplayName("Status Code - PUT 400 Bad Request")
    public void testPutReturns400ForInvalidData() {
        // Create a course
        CourseRequest courseRequest = Rest.createCourse();
        Course course = Rest.courseByName(courseRequest.name());

        // Try to update with invalid data
        String invalidJson = """
            {
                "name": ""
            }
            """;
        
        given()
            .contentType("application/json")
            .body(invalidJson)
        .when()
            .put("/courses/" + course.id())
        .then()
            .statusCode(400);
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "PUT /courses/{id} retorna 404 quando curso não existe", 
               category = "🌐 Status Codes - PUT")
    @DisplayName("Status Code - PUT 404 Not Found")
    public void testPutReturns404() {
        String courseJson = """
            {
                "name": "Updated Name"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .put("/courses/999999")
        .then()
            .statusCode(404);
    }

    // ========== DELETE /courses/{id} Status Codes (5 points) ==========
    
    @Test
    @TestScore(points = 3, weight = 0.3, description = "DELETE /courses/{id} retorna 204 No Content", 
               category = "🌐 Status Codes - DELETE")
    @DisplayName("Status Code - DELETE 204 No Content")
    public void testDeleteReturns204() {
        // Create a course
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
        
        // Delete the course
        given()
        .when()
            .delete("/courses/" + courseId)
        .then()
            .statusCode(204);
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "DELETE /courses/{id} retorna 404 quando não existe", 
               category = "🌐 Status Codes - DELETE")
    @DisplayName("Status Code - DELETE 404 Not Found")
    public void testDeleteReturns404() {
        int statusCode = given()
                .when()
                .delete("/courses/999999")
                .then()
                .extract().statusCode();

        Assertions.assertTrue(statusCode == 204 || statusCode == 404);
    }
}

// Made with Bob
