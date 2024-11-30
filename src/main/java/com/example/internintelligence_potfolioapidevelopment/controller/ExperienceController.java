package com.example.internintelligence_potfolioapidevelopment.controller;

import com.example.internintelligence_potfolioapidevelopment.dto.ExperienceDto;
import com.example.internintelligence_potfolioapidevelopment.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;

    @Operation(summary = "Retrieves another user's experience list")
    @GetMapping("/users/{userId}")
    public List<ExperienceDto> getAllExperienceByUserId(@PathVariable Integer userId){
        return experienceService.getAllExperienceByUserId(userId);
    }

    @Operation(summary = "Retrieves the authenticated user's experience list")
    @GetMapping("/me")
    public List<ExperienceDto> getOwnExperience(HttpServletRequest request){
        return experienceService.getExperience(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ExperienceDto addExperience(HttpServletRequest request, @Validated @RequestBody ExperienceDto experienceDto){
        return experienceService.addExperience(request,experienceDto);
    }

    @PutMapping("/edit/{experienceId}")
    public ExperienceDto editExperience(HttpServletRequest request,@PathVariable Integer experienceId,@Validated @RequestBody ExperienceDto experienceDto){
        return experienceService.editExperience(request,experienceId,experienceDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{experienceId}")
    public void deleteExperience(HttpServletRequest request,@PathVariable Integer experienceId){
        experienceService.deleteExperience(request,experienceId);
    }


}
