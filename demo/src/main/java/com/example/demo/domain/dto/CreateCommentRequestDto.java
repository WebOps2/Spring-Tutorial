package com.example.demo.domain.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentRequestDto(
    @NotBlank(message = AUTHOR_REQUIRED)
    @Size(max = 255, message = AUTHOR_MAX_LENGTH)
    String author,
    @NotBlank(message = CONTENT_REQUIRED)
    @Size(max = 1000, message = CONTENT_MAX_LENGTH)
    String content,
    @NotNull(message = TASK_ID_REQUIRED)
    UUID taskId

) {
    public static final String AUTHOR_REQUIRED = "Author is required";
    public static final String CONTENT_REQUIRED = "Content is required";
    public static final String TASK_ID_REQUIRED = "Task ID is required";
    public static final String AUTHOR_MAX_LENGTH = "Author must be less than 255 characters";
    public static final String CONTENT_MAX_LENGTH = "Content must be less than 1000 characters";
    public static final String CREATED_AT_REQUIRED = "Created at is required";
}
