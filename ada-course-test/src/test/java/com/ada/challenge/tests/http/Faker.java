package com.ada.challenge.tests.http;

import org.instancio.Instancio;

public class Faker {

    public static String courseName() {
        return "Course " + Instancio.create(String.class);
    }

    public static String lessonName() {
        return "Lesson " + Instancio.create(String.class);
    }
}
