package dev.matheuscruz.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonDTO(
        @NotNull
        @NotBlank
        String name,
        @JsonIgnore
        Long id) {}
