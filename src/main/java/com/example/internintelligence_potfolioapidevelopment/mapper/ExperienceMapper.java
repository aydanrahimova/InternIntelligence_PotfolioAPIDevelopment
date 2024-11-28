package com.example.internintelligence_potfolioapidevelopment.mapper;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Experience;
import com.example.internintelligence_potfolioapidevelopment.dto.ExperienceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExperienceMapper{
    Experience toEntity(ExperienceDto dto);
    ExperienceDto toDto(Experience entity);
    void mapToUpdate(@MappingTarget Experience experience,ExperienceDto experienceDto);
}
