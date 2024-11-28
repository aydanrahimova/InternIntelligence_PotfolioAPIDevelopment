package com.example.internintelligence_potfolioapidevelopment.dto;

import com.example.internintelligence_potfolioapidevelopment.validation.ValidDateRange;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange(startField = "startTime", endField = "endTime")
public class ProjectDto {
    @NotBlank(message = "Project name is required.")
    @Size(max = 255, message = "The length of the name must not exceed 255 characters")
    private String name;//255
    @Size(max = 2000,message = "The length of the description must not exceed 255 characters")
    private String description;//2000
    @PastOrPresent(message = "Start time must be in past or present")
    private LocalDate startTime;
    @PastOrPresent(message = "End time must be in past or present")
    private LocalDate endTime;
    @URL
    private String projectUrl;
}
