package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import com.example.demo.domain.CreateProjectRequest;
import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateProjectRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.entity.Project;
import com.example.demo.domain.entity.Task;

public interface ProjectService {
    Project createProject(CreateProjectRequest request);
    Task addTaskToProject(UUID projectId, CreateTaskRequest request);
    List<Task> getTasksByProjectId(UUID projectId);
    List<Project> getAllProjects();
    List<Project> getAllProjectsWithTasks();
    Task updateTaskInProject(UUID projectId, UUID taskId, UpdateTaskRequest request);
    Project updateProject(UUID projectId, UpdateProjectRequest request);
    void deleteTaskFromProject(UUID projectId, UUID taskId);
    void deleteProject(UUID projectId);
}
