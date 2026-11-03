package com.example.demo.Mapper;

import com.example.demo.domain.CreateProjectRequest;
import com.example.demo.domain.UpdateProjectRequest;
import com.example.demo.domain.dto.CreateProjectRequestDto;
import com.example.demo.domain.dto.ProjectDto;
import com.example.demo.domain.dto.UpdateProjectRequestDto;
import com.example.demo.domain.entity.Project;

public interface ProjectMapper {
    CreateProjectRequest fromDto(CreateProjectRequestDto createProjectRequestDto);
    UpdateProjectRequest fromDto(UpdateProjectRequestDto updateProjectRequestDto);
    ProjectDto toDto(Project project);
}
