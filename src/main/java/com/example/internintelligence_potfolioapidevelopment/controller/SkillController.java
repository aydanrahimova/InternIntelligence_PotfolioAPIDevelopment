package com.example.internintelligence_potfolioapidevelopment.controller;

import com.example.internintelligence_potfolioapidevelopment.dto.ExperienceDto;
import com.example.internintelligence_potfolioapidevelopment.dto.SkillDto;
import com.example.internintelligence_potfolioapidevelopment.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @Operation(summary = "Retrieves another user's skills list")
    @GetMapping("/users/{userId}")
    public List<SkillDto> getAllSkillsByUserId(@PathVariable Long userId){
        return skillService.getAllSkillsByUserId(userId);
    }

    @Operation(summary = "Retrieves the authenticated user's skills list")
    @GetMapping("/me")
    public List<SkillDto> getOwnExperience(HttpServletRequest request){
        return skillService.getOwnSkills(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public SkillDto addExperience(HttpServletRequest request, @Validated @RequestBody SkillDto skillDto){
        return skillService.addSkill(request,skillDto);
    }

    @PutMapping("/edit/{skillId}")
    public SkillDto editExperience(HttpServletRequest request, @PathVariable Long skillId, @Validated @RequestBody SkillDto skillDto){
        return skillService.editSkill(request, skillId,skillDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{skillId}")
    public void deleteExperience(HttpServletRequest request,@PathVariable Long skillId){
        skillService.deleteSkill(request,skillId);
    }



}
