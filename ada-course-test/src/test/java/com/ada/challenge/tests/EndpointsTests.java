package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import com.ada.challenge.tests.http.Course;
import com.ada.challenge.tests.http.CourseRequest;
import com.ada.challenge.tests.http.Lesson;
import com.ada.challenge.tests.http.LessonRequest;
import com.ada.challenge.tests.http.Rest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.ada.challenge.tests.http.Rest.createLesson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for REST Endpoints
 * Total: 15 points mandatory + 6 points optional
 */
@DisplayName("📡 Endpoints REST")
public class EndpointsTests extends BaseTest {

    @BeforeEach
    public void beforeEach() {
        String name = Instancio.create(String.class);
        String courseJson = """
                {
                    "name": "%s"
                }
                """.formatted(name);

        given()
                .contentType("application/json")
                .body(courseJson)
                .when()
                .post("/courses")
                .then()
                .statusCode(201);
    }


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
        String courseName = Instancio.create(String.class);
        String courseJson = """
                {
                    "name": "%s"
                }
                """.formatted(courseName);

        given()
                .contentType("application/json")
                .body(courseJson)
                .when()
                .post("/courses")
                .then()
                .statusCode(201);


        Map<String, Object> course = getCourseByName(courseName);

        // Test the endpoint
        given()
                .when()
                .get("/courses/" + course.get("id"))
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint PUT /courses/{id} existe e funciona", category = "📡 Endpoints")
    @DisplayName("Endpoint - PUT /courses/{id}")
    public void testPutCourseEndpoint() {
        // Create a course first
        String courseName = Instancio.create(String.class);

        String courseJson = """
                {
                    "name": "%s"
                }
                """.formatted(courseName);

        given()
                .contentType("application/json")
                .body(courseJson)
                .when()
                .post("/courses")
                .then()
                .statusCode(201);

        String newCourseName = Instancio.create(String.class);
        String updatedJson = """
                {
                    "name": "%s"
                }
                """.formatted(newCourseName);

        Map<String, Object> course = getCourseByName(courseName);

        given()
                .contentType("application/json")
                .body(updatedJson)
                .when()
                .put("/courses/" + course.get("id"))
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    private static Map<String, Object> getCourseByName(String courseName) {
        List<Map<String, Object>> courses = given()
                .when()
                .get("/courses")
                .then()
                .extract().body()
                .as(new TypeRef<List<Map<String, Object>>>() {
                });

        Map<String, Object> course = courses.stream().filter(c -> c.get("name").equals(courseName)).findAny().get();
        return course;
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

        given()
                .contentType("application/json")
                .body(courseJson)
                .when()
                .post("/courses")
                .then()
                .statusCode(201);

        // Test the endpoint
        given()
                .when()
                .delete("/courses/" + 1)
                .then()
                .statusCode(204);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint POST /courses/{courseId}/lessons existe e funciona",
            category = "📡 Endpoints", mandatory = false)
    @DisplayName("Endpoint - POST /courses/{courseId}/lessons (PLUS)")
    public void testPostLessonEndpoint() {
        String courseName = Instancio.create(String.class);
        // Create a course first
        String courseJson = """
                {
                    "name": "%s"
                }
                """.formatted(courseName);

        given()
                .contentType("application/json")
                .body(courseJson)
                .when()
                .post("/courses")
                .then()
                .statusCode(201);

        String lessonName = Instancio.create(String.class);

        // Create a lesson
        String lessonJson = """
                {
                    "name": "%s"
                }
                """.formatted(lessonName);

        Map<String, Object> course = getCourseByName(courseName);

        given()
                .contentType("application/json")
                .body(lessonJson)
                .when()
                .post("/courses/" + course.get("id") + "/lessons")
                .then()
                .statusCode(201)
                .contentType("application/json");
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Endpoint GET /courses/{courseId}/lessons existe e funciona",
            category = "📡 Endpoints", mandatory = false)
    @DisplayName("Endpoint - GET /courses/{courseId}/lessons (PLUS)")
    public void testGetLessonsEndpoint() {
        CourseRequest courseRequest = Rest.createCourse();
        Course course = Rest.courseByName(courseRequest.name());

        String lessonName = Instancio.create(String.class);
        LessonRequest lessonRequest = Rest.createLesson(lessonName, course.id());

        Rest.createLesson(lessonRequest.name(), course.id());

        List<Lesson> lessons = RestAssured.given()
                        .get("/courses/" + course.id() + "/lessons")
                                .then()
                                        .statusCode(200)
                                                .extract()
                                                        .body()
                                                                .as(new TypeRef<>() {
                                                                });


        Assertions.assertDoesNotThrow(() -> {
            lessons.stream().filter(lesson -> lesson.name().equals(lessonName)).findAny().orElseThrow();
        });
    }
}

// Made with Bob
