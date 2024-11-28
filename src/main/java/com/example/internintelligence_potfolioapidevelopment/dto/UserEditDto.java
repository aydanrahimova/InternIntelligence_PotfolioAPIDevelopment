package com.example.internintelligence_potfolioapidevelopment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {
    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contain letters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can only contain letters.")
    private String lastName;

    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters.")
    private String title;

    @Size(max = 500, message = "About section cannot exceed 500 characters.")
    private String about;

    @Pattern(regexp = "^\\+?[1-9][0-9]{1,14}$", message = "Phone number must be in a valid format, e.g., +1234567890.")
    private String phone;

    @Past(message = "Birthday must be in past.")
    private LocalDate birthDay;
}