package com.example.internintelligence_potfolioapidevelopment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SkillDto {
    @NotBlank(message = "Skill is required.")
    private String skillName;
    private String level;
}
