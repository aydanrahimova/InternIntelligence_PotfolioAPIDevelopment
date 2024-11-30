
package com.example.internintelligence_potfolioapidevelopment.controller;

import com.example.internintelligence_potfolioapidevelopment.dto.ProjectDto;
import com.example.internintelligence_potfolioapidevelopment.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Retrieves another user's project list")
    @GetMapping("/users/{userId}")
    public List<ProjectDto> getProjectsByUserId(@PathVariable Integer userId) {
        return projectService.getAllProjectsByUserId(userId);
    }

    @Operation(summary = "Retrieves the authenticated user's project list")
    @GetMapping("/me")
    public List<ProjectDto> getOwnProjects(HttpServletRequest request) {
        return projectService.getOwnProjects(request);
    }

    @PostMapping("/add")
    public ProjectDto addProject(HttpServletRequest request, @Validated @RequestBody ProjectDto project) {
        return projectService.addProject(request, project);
    }

    @PutMapping("/edit/{projectId}")
    public ProjectDto editProject(HttpServletRequest request, @PathVariable Integer projectId, @Validated @RequestBody ProjectDto project) {
        return projectService.editProject(request, projectId, project);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(HttpServletRequest request, @PathVariable Integer projectId) {
        projectService.deleteProject(request, projectId);
    }
}

