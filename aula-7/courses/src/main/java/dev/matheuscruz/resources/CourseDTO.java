package dev.matheuscruz.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseDTO(
        @NotNull(message = "must not be null")
        @NotBlank(message = "must not be blank")
        @Size(min = 3)
        String name) {
}
