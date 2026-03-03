package dev.matheuscruz;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// @Entity
// PanacheEntity
// No-arg constructor
@Entity
@Table(name = "course")
public class Course extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    protected Course() {
    }

    public Course(String name) {
        this.name = name;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setName(String name) {
        String newName = Objects.requireNonNull(name, "name must not be null");
        if (newName.length() >= 3) {
            this.name = newName;
        }
        // TODO: ...
    }
}
