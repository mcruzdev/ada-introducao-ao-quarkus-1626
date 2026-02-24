package dev.matheuscruz;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

// @Entity
// PanacheEntity
// No-arg constructor
@Entity
@Table(name = "course")
public class Course extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
            length = 222,
            unique = true,
            nullable = false)
    private String name;


    @OneToMany
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
}
