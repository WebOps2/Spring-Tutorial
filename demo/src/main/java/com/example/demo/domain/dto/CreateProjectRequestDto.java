package com.example.demo.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequestDto(
    @NotBlank(message = ERROR_MESSAGE_NAME_REQUIRED)
    @Size(max = 255, message = ERROR_MESSAGE_NAME_LENGTH)
    String name

) {
    private static final String ERROR_MESSAGE_NAME_REQUIRED = "Name is required";
    private static final String ERROR_MESSAGE_NAME_LENGTH = "Name must be less than 255 characters";
}
