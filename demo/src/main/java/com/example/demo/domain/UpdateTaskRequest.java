package com.example.demo.domain;

import java.time.LocalDate;

import com.example.demo.domain.entity.TaskPriority;
import com.example.demo.domain.entity.TaskStatus;
public record UpdateTaskRequest(String title, String description, LocalDate dueDate, TaskStatus status, TaskPriority priority) {
}
