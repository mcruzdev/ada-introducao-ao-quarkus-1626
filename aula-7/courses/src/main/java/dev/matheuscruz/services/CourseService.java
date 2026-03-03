package dev.matheuscruz.services;


import dev.matheuscruz.Course;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CourseService {

    @Transactional
    public Course createCourse(Course course) {
        course.persist();
        return course;
    }
}
