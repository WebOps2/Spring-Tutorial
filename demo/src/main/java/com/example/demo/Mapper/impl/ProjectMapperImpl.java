package com.example.demo.Mapper.impl;

import org.springframework.stereotype.Component;

import com.example.demo.Mapper.ProjectMapper;
import com.example.demo.Mapper.TaskMapper;
import com.example.demo.domain.CreateProjectRequest;
import com.example.demo.domain.UpdateProjectRequest;
import com.example.demo.domain.dto.CreateProjectRequestDto;
import com.example.demo.domain.dto.ProjectDto;
import com.example.demo.domain.dto.UpdateProjectRequestDto;
import com.example.demo.domain.entity.Project;

@Component
public class ProjectMapperImpl implements ProjectMapper {

    private final TaskMapper taskMapper;

    public ProjectMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public CreateProjectRequest fromDto(CreateProjectRequestDto createProjectRequestDto) {
        return new CreateProjectRequest(createProjectRequestDto.name());
    }

    @Override
    public ProjectDto toDto(Project project) {
        return new ProjectDto(project.getId(), project.getName(), project.getTasks().stream().map(taskMapper::toDto).toList());
    }

    @Override
    public UpdateProjectRequest fromDto(UpdateProjectRequestDto updateProjectRequestDto) {
        return new UpdateProjectRequest(updateProjectRequestDto.name());
    }
}
