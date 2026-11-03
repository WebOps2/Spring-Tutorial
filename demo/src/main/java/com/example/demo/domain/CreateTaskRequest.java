package com.example.demo.domain;

import java.time.LocalDate;

import com.example.demo.domain.entity.TaskPriority;

public record CreateTaskRequest(String title, String description, LocalDate dueDate, TaskPriority priority) {
}
