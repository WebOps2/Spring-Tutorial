package com.example.demo.domain.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.domain.entity.TaskPriority;
import com.example.demo.domain.entity.TaskStatus;

public record TaskDto(
    UUID id,
    String title,
    String description,
    LocalDate dueDate,
    TaskStatus status,
    TaskPriority priority,
    Instant createdAt,
    Instant updatedAt
) {
}
