package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Mapper.ProjectMapper;
import com.example.demo.Mapper.TaskMapper;
import com.example.demo.domain.CreateProjectRequest;
import com.example.demo.domain.CreateTaskRequest;
import com.example.demo.domain.UpdateProjectRequest;
import com.example.demo.domain.UpdateTaskRequest;
import com.example.demo.domain.dto.CreateProjectRequestDto;
import com.example.demo.domain.dto.CreateTaskRequestDto;
import com.example.demo.domain.dto.ProjectDto;
import com.example.demo.domain.dto.TaskDto;
import com.example.demo.domain.dto.UpdateProjectRequestDto;
import com.example.demo.domain.dto.UpdateTaskRequestDto;
import com.example.demo.domain.entity.Project;
import com.example.demo.domain.entity.Task;
import com.example.demo.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, TaskMapper taskMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        CreateProjectRequest createProjectRequest = projectMapper.fromDto(createProjectRequestDto);
        Project project = projectService.createProject(createProjectRequest);
        ProjectDto createdProjectDto = projectMapper.toDto(project);
        return new ResponseEntity<>(createdProjectDto, HttpStatus.CREATED);
    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<TaskDto> addTaskToProject(@PathVariable UUID projectId, @Valid @RequestBody CreateTaskRequestDto createTaskRequestDto) {
        CreateTaskRequest createTaskRequest = taskMapper.fromDto(createTaskRequestDto);
        Task task = projectService.addTaskToProject(projectId, createTaskRequest);
        TaskDto createdTaskDto = taskMapper.toDto(task);
        return new ResponseEntity<>(createdTaskDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDto> projectDtos = projects.stream().map(projectMapper::toDto).toList();
        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }

    @GetMapping("/with-tasks")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithTasks() {
        List<Project> projects = projectService.getAllProjectsWithTasks();
        List<ProjectDto> projectDtos = projects.stream().map(projectMapper::toDto).toList();
        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByProjectId(@PathVariable UUID projectId) {
        List<Task> tasks = projectService.getTasksByProjectId(projectId);
        List<TaskDto> taskDtos = tasks.stream().map(taskMapper::toDto).toList();
        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable UUID projectId, @Valid @RequestBody UpdateProjectRequestDto updateProjectRequestDto) {
        UpdateProjectRequest updateProjectRequest = projectMapper.fromDto(updateProjectRequestDto);
        Project project = projectService.updateProject(projectId, updateProjectRequest);
        ProjectDto updatedProjectDto = projectMapper.toDto(project);
        return new ResponseEntity<>(updatedProjectDto, HttpStatus.OK);
    }
    @PutMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> updateTaskInProject(@PathVariable UUID projectId, @PathVariable UUID taskId, @Valid @RequestBody UpdateTaskRequestDto updateTaskRequestDto) {
        UpdateTaskRequest updateTaskRequest = taskMapper.fromDto(updateTaskRequestDto);
        Task task = projectService.updateTaskInProject(projectId, taskId, updateTaskRequest);
        TaskDto updatedTaskDto = taskMapper.toDto(task);
        return new ResponseEntity<>(updatedTaskDto, HttpStatus.OK);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTaskFromProject(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        projectService.deleteTaskFromProject(projectId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
