package com.example.internintelligence_potfolioapidevelopment.dto;


import com.example.internintelligence_potfolioapidevelopment.enums.EmploymentType;
import com.example.internintelligence_potfolioapidevelopment.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange(startField = "startTime", endField = "endTime")
public class ExperienceDto {
    @NotBlank(message = "Title is required.")
    private String title;
    @Size(max = 2000,message = "The length of the description must not exceed 255 characters")
    private String description;
    @NotBlank(message = "Organization name is required.")
    private String organizationName;
    private EmploymentType employmentType;
    private LocalDate startTime;
    private LocalDate endTime;
    private List<String> skills;
}
