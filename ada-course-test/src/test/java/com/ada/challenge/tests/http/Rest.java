package com.ada.challenge.tests.http;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class Rest {

    public static CourseRequest createCourse() {
        return createCourseWithName(Faker.courseName());
    }

    public static CourseRequest createCourseWithName(String name) {
        Objects.requireNonNull(name, "name must not be null");
        CourseRequest courseRequest = new CourseRequest(name);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(courseRequest)
                .post("/courses")
                .then()
                .statusCode(201);
        return courseRequest;
    }

    public static Course courseByName(String courseName) {

        List<Course> courses = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/courses")
                .then()
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        return courses.stream().filter(c -> c.name().equals(courseName)).findFirst().orElseThrow();

    }

    public static LessonRequest createLesson(String lessonName, Long courseId) {
        LessonRequest lessonRequest = new LessonRequest(lessonName);
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(lessonRequest)
                .post("/courses/" + courseId + "/lessons")
                .then()
                .statusCode(201);
        return lessonRequest;
    }

}
