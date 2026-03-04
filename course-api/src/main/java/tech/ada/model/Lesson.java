package tech.ada.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "lessons")
public class Lesson extends PanacheEntity {

    private String name;

    // required for JPA
    protected Lesson() {
    }

    public Lesson(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
