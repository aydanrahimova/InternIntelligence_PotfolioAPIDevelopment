package com.example.internintelligence_potfolioapidevelopment.mapper;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Education;
import com.example.internintelligence_potfolioapidevelopment.dto.EducationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EducationMapper {
    Education toEntity(EducationDto dto);
    EducationDto toDto(Education entity);
    void mapForUpdate(@MappingTarget Education education,EducationDto educationDto);
}
