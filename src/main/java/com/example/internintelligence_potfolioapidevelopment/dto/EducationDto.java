package com.example.internintelligence_potfolioapidevelopment.dto;

import com.example.internintelligence_potfolioapidevelopment.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange(startField = "startTime", endField = "endTime")
public class EducationDto {
    @NotBlank(message = "School is required.")
    private String schoolName;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startTime;
    private LocalDate endTime;
}
