package tech.ada.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course extends PanacheEntity {

    private String name;

    @OneToMany
    private final List<Lesson> lessons = new ArrayList<>();

    // required for JPA
    protected Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addLesson(Lesson lesson) {
        Lesson validatedLesson = Objects.requireNonNull(lesson, "lesson must no be null");
        this.lessons.add(validatedLesson);
    }

    public void changeName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public List<Lesson> getLessons() {
        // defensive
        return Collections.unmodifiableList(this.lessons);
    }
}
