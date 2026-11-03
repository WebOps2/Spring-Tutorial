package com.example.demo.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.entity.Task;
import com.example.demo.domain.entity.TaskStatus;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(CreateTaskRequest request) {
        Instant now = Instant.now();
        Task task = new Task(
            null,
            request.title(),
            request.description(),
            request.dueDate(),
            TaskStatus.OPEN,
            request.priority(),
            now,
            now
        );

        return taskRepository.save(task);
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.findAll(Sort.by(Direction.DESC, "createdAt"));
    }

    @Override
    public Task updateTask(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }
}
