package com.example.internintelligence_potfolioapidevelopment.service;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Project;
import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.ProjectRepo;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.UserRepo;
import com.example.internintelligence_potfolioapidevelopment.dto.ProjectDto;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_potfolioapidevelopment.mapper.ProjectMapper;
import com.example.internintelligence_potfolioapidevelopment.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final JwtUtil jwtUtil;

    public List<ProjectDto> getOwnProjects(HttpServletRequest request) {
        Long userId = extractUserIdFromToken(request);
        log.info("Operation of getting projects of the authenticated user started...");
        return projectRepo.findAllByUserId(userId).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    public ProjectDto addProject(HttpServletRequest request, ProjectDto projectDto) {
        Long userId = extractUserIdFromToken(request);
        log.info("Operation of adding new project for user with id {} started...", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} not found.", userId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        Project project = projectMapper.toEntity(projectDto);
        project.setUser(user);
        projectRepo.save(project);
        log.info("New project added for user with id {}", userId);
        return projectMapper.toDto(project);
    }

    public void deleteProject(HttpServletRequest request, Long projectId) {
        Long userId = extractUserIdFromToken(request);
        log.info("Operation of deleting project with id {} for user with id {} started...", projectId, userId);
        Project project = projectRepo.findById(projectId).orElseThrow(() -> {
            log.warn("Failed to delete project: Project with id {} not found.", projectId);
            return new ResourceNotFoundException("PROJECT_NOT_FOUND");
        });
        projectRepo.delete(project);
        log.info("Project deleted successfully for user with id {}", userId);
    }

    public ProjectDto editProject(HttpServletRequest request, Long projectId, ProjectDto projectDto) {
        Long userId = extractUserIdFromToken(request);
        log.info("Process of editing project with id {} for user with id {} started....", projectId, userId);
        Project project = projectRepo.findById(projectId).orElseThrow(() -> {
            log.warn("Failed to edit project: Project with id {} not found.", projectId);
            return new ResourceNotFoundException("PROJECT_NOT_FOUND");
        });
        projectMapper.mapToUpdate(project, projectDto);
        projectRepo.save(project);
        log.info("Project with id {} successfully updated.", projectId);
        return projectMapper.toDto(project);
    }

    private Long extractUserIdFromToken(HttpServletRequest request) {
        return jwtUtil.getUserId(jwtUtil.resolveClaims(request));
    }

    public List<ProjectDto> getAllProjectsByUserId(Long userId){
        log.info("Operation of getting projects of the user with id {} started...",userId);
        if (!userRepo.existsById(userId)){
            log.warn("Failed to get projects: User with id {} not found",userId);
            throw new ResourceNotFoundException("USER_NOT_FOUND");
        }
        List<ProjectDto> projectDtoList = projectRepo.findAllByUserId(userId)
                .stream()
                .map(projectMapper::toDto)
                .toList();
        log.info("Projects of the user with id {} returned.",userId);
        return projectDtoList;
    }
}
