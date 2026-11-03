package com.example.demo.Mapper.impl;

import org.springframework.stereotype.Component;

import com.example.demo.Mapper.TaskMapper;
import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.dto.UpdateTaskRequestDto;
import com.example.demo.domain.entity.Task;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public CreateTaskRequest fromDto(CreateTaskRequestDto dto) {
        return new CreateTaskRequest(dto.title(), dto.description(), dto.dueDate(), dto.priority());
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getPriority(), task.getCreatedAt(), task.getUpdatedAt());
    }

    @Override
    public UpdateTaskRequest fromDto(UpdateTaskRequestDto dto) {
        return new UpdateTaskRequest(dto.title(), dto.description(), dto.dueDate(), dto.status(), dto.priority());
    }
}
