package dev.matheuscruz;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Lesson extends PanacheEntity {

    @Column(unique = true, nullable = false)
    private String name;

    protected Lesson() {}

    public Lesson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
