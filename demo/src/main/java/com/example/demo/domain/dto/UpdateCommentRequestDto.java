package com.example.demo.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequestDto(
    @NotBlank(message = CONTENT_REQUIRED)
    String content
) {
    public static final String CONTENT_REQUIRED = "Content is required for updating a comment";
}
