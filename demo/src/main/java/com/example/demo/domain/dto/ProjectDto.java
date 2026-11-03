package com.example.demo.domain.dto;

import java.util.List;
import java.util.UUID;

public record ProjectDto(
    UUID id,
    String name,
    List<TaskDto> tasks
) {
}
