package com.ada.challenge.tests.http;

import java.util.List;

public record Course(Long id, String name, List<Lesson> lessons) {
}
