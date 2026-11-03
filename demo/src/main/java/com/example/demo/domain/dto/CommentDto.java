package com.example.demo.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDto(
    UUID id,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String author,
    String content,
    UUID taskId
) {
}
