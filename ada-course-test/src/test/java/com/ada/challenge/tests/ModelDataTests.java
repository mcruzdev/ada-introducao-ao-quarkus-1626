package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import com.ada.challenge.tests.http.Course;
import com.ada.challenge.tests.http.CourseRequest;
import com.ada.challenge.tests.http.Faker;
import com.ada.challenge.tests.http.Lesson;
import com.ada.challenge.tests.http.LessonRequest;
import com.ada.challenge.tests.http.Rest;
import io.restassured.common.mapper.TypeRef;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Model Data requirements
 * Total: 15 points (Course: 10 points, Lesson: 5 points)
 */
@DisplayName("📦 Modelo de Dados")
public class ModelDataTests extends BaseTest {

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Course deve ter campo 'id' (Long)", category = "📦 Modelo de Dados")
    @DisplayName("Course - Campo id")
    public void testCourseHasIdField() {
        // Create a course and verify it returns an id
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
            .statusCode(201)
            .body("id", notNullValue())
            .body("id", instanceOf(Number.class));
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Course deve ter campo 'name' (String)", category = "📦 Modelo de Dados")
    @DisplayName("Course - Campo name")
    public void testCourseHasNameField() {
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
            .statusCode(201)
            .body("name", equalTo("Test Course"))
            .body("name", instanceOf(String.class));
    }

    @Test
    @TestScore(points = 4, weight = 0.4, description = "Course deve ter campo 'lessons' (List<Lesson>)", category = "📦 Modelo de Dados")
    @DisplayName("Course - Campo lessons")
    public void testCourseHasLessonsField() {
        CourseRequest courseRequest = Rest.createCourse();
        Course course = Rest.courseByName(courseRequest.name());

        String lessonName = Faker.lessonName();
        LessonRequest lessonRequest = new LessonRequest(lessonName);

        given()
                .contentType("application/json")
                .body(lessonRequest)
                .when()
                .post("/courses/" + course.id() + "/lessons")
                .then()
                .statusCode(201);
        
        given()
            .accept("application/json")
        .when()
            .get("/courses/" + course.id())
        .then()
            .statusCode(200)
            .body("lessons", notNullValue())
            .body("lessons", instanceOf(java.util.List.class));
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "Lesson deve ter campo 'id' (Long)", category = "📦 Modelo de Dados")
    @DisplayName("Lesson - Campo id")
    public void testLessonHasIdField() {
        // First create a course
        CourseRequest courseRequest = Rest.createCourse();
        Course course = Rest.courseByName(courseRequest.name());

        String lessonName = Faker.lessonName();
        LessonRequest lessonRequest = new LessonRequest(lessonName);

        given()
            .contentType("application/json")
            .body(lessonRequest)
        .when()
            .post("/courses/" + course.id() + "/lessons")
        .then()
            .statusCode(201);

        List<Lesson> allCourseLessons = given()
                .contentType("application/json")
                .get("/courses/" + course.id() + "/lessons")
                .then()
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        Lesson lessonForChecking = allCourseLessons.stream().filter(l -> l.name().equals(lessonName)).findFirst().orElseThrow();

        Assertions.assertNotNull(lessonForChecking.name());
        Assertions.assertNotNull(lessonForChecking.id());
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Lesson deve ter campo 'name' (String)", category = "📦 Modelo de Dados")
    @DisplayName("Lesson - Campo name")
    public void testLessonHasNameField() {
        CourseRequest courseRequest = Rest.createCourse();
        Course course = Rest.courseByName(courseRequest.name());

        String lessonName = Faker.lessonName();
        LessonRequest lessonRequest = new LessonRequest(lessonName);

        given()
            .contentType("application/json")
            .body(lessonRequest)
        .when()
            .post("/courses/" + course.id() + "/lessons")
        .then()
            .statusCode(201);

        List<Lesson> allCoursesLesson = given()
                .contentType("application/json")
                .get("/courses/" + course.id() + "/lessons")
                .then()
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        Lesson lessonForChecking = allCoursesLesson.stream().filter(l -> l.name().equals(lessonName)).findFirst().orElseThrow();

        Assertions.assertNotNull(lessonForChecking.name());
        Assertions.assertNotNull(lessonForChecking.id());
    }
}

// Made with Bob
