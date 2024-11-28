package com.example.internintelligence_potfolioapidevelopment.mapper;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Project;
import com.example.internintelligence_potfolioapidevelopment.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto dto);
    ProjectDto toDto(Project project);
    void mapToUpdate(@MappingTarget Project project,ProjectDto projectDto);
}
