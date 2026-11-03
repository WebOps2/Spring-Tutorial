package com.example.demo.Mapper;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.dto.UpdateTaskRequestDto;
import com.example.demo.domain.entity.Task;

public interface TaskMapper {
    CreateTaskRequest fromDto(CreateTaskRequestDto dto);
    TaskDto toDto(Task task);
    UpdateTaskRequest fromDto(UpdateTaskRequestDto dto);
}

