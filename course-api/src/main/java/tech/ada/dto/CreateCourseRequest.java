package tech.ada.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseRequest(

        @NotNull @NotBlank @Size(min = 3) String name) {
}
