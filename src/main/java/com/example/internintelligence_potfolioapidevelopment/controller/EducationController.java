package com.example.internintelligence_potfolioapidevelopment.controller;

import com.example.internintelligence_potfolioapidevelopment.dto.EducationDto;
import com.example.internintelligence_potfolioapidevelopment.service.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @Operation(summary = "Retrieves another user's education list")
    @GetMapping("/users/{userId}")
    public List<EducationDto> getAllEducationByUserId(@PathVariable Long userId){
        return educationService.getAllEducationByUserId(userId);
    }

    @Operation(summary = "Retrieve the authenticated user's education list")
    @GetMapping("/me")
    public List<EducationDto> getAllOwnEducation(HttpServletRequest request) {
        return educationService.getAllOwnEducation(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/me/add")
    public EducationDto addEducation(HttpServletRequest request, @Validated @RequestBody EducationDto dto) {
        return educationService.addEducation(request, dto);
    }

    @PutMapping("/me/edit/{eductionId}")
    public EducationDto editEducationOfUser(HttpServletRequest request, @PathVariable Long eductionId, @Validated @RequestBody EducationDto educationDto) {
        return educationService.editEducationOfUser(request, eductionId, educationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me/delete/{educationId}")
    public void deleteEducationOfUser(HttpServletRequest request, @PathVariable Long educationId) {
        educationService.deleteEducationOfUser(request, educationId);
    }



}