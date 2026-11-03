package com.example.demo.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.CreateProjectRequest;
import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateProjectRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.entity.Project;
import com.example.demo.domain.entity.Task;
import com.example.demo.domain.entity.TaskStatus;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(CreateProjectRequest request) {
        Project project = new Project(
            null,
            request.name(),
            new ArrayList<Task>()
        );
        return projectRepository.save(project);
    }

    @Override
    public Task addTaskToProject(UUID projectId, CreateTaskRequest request) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
        Task task = new Task(
            null,
            request.title(),
            request.description(),
            request.dueDate(),
            TaskStatus.OPEN,
            request.priority(),
            Instant.now(),
            Instant.now()
        );
        task.setProject(project);
        return taskRepository.save(task);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Task> getTasksByProjectId(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
        return project.getTasks();
    }

    @Override
    public List<Project> getAllProjectsWithTasks() {
        return projectRepository.findAllWithTasks();
    }

    @Override
    public Task updateTaskInProject(UUID projectId, UUID taskId, UpdateTaskRequest request) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
        Task task = project.getTasks().stream().filter(t -> t.getId().equals(taskId)).findFirst()
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }

    @Override
    public Project updateProject(UUID projectId, UpdateProjectRequest request) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
        project.setName(request.name());
        return projectRepository.save(project);
    }

    @Override
    public void deleteTaskFromProject(UUID projectId, UUID taskId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        Task task = project.getTasks().stream().filter(t -> t.getId().equals(taskId)).findFirst()
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        project.getTasks().remove(task);
        taskRepository.delete(task);

    }

    @Override
    public void deleteProject(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
        projectRepository.delete(project);
    }
}
