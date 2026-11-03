package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.entity.Task;

public interface TaskService {

    Task createTask(CreateTaskRequest request);
    List<Task> listTasks();
    Task updateTask(UUID id, UpdateTaskRequest request);
    void deleteTask(UUID id);
}
