package com.example.internintelligence_potfolioapidevelopment.mapper;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Skill;
import com.example.internintelligence_potfolioapidevelopment.dto.SkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SkillMapper{
    Skill toEntity(SkillDto skillDto);
    SkillDto toDto(Skill skill);

    void mapToUpdate(@MappingTarget Skill skill, SkillDto skillDto);
}
