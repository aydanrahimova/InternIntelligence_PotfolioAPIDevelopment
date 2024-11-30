package com.example.internintelligence_potfolioapidevelopment.service;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Experience;
import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.ExperienceRepo;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.UserRepo;
import com.example.internintelligence_potfolioapidevelopment.dto.ExperienceDto;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_potfolioapidevelopment.mapper.ExperienceMapper;
import com.example.internintelligence_potfolioapidevelopment.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExperienceService {
    private final ExperienceRepo experienceRepo;
    private final UserRepo userRepo;
    private final ExperienceMapper experienceMapper;
    private final JwtUtil jwtUtil;

    public List<ExperienceDto> getExperience(HttpServletRequest request) {
        log.info("Operation of getting experience of the user is started...");
        Integer userId = extractUserIdFromToken(request);
        List<ExperienceDto> experienceDtoList = experienceRepo.findAllByUserId(userId)
                .stream()
                .map(experienceMapper::toDto)
                .toList();
        log.info("Experience records for user with id {} returned", userId);
        return experienceDtoList;
    }

    public ExperienceDto addExperience(HttpServletRequest request, ExperienceDto experienceDto) {
        Integer userId = extractUserIdFromToken(request);
        log.info("Operation of adding new experience to the user is started...");
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} not found.", userId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        Experience experience = experienceMapper.toEntity(experienceDto);
        experience.setUser(user);
        experienceRepo.save(experience);
        log.info("New experience to user with id {} is added", userId);
        return experienceMapper.toDto(experience);
    }

    public void deleteExperience(HttpServletRequest request, Integer experienceId) {
        Integer userId = extractUserIdFromToken(request);
        log.info("Operation of deleting experience of the user is started....");
//        User user = userRepo.findById(userId).orElseThrow(() -> {
//            log.info("User with id {} not found", userId);
//            return new ResourceNotFoundException("USER_NOT_FOUND");
//        });
        Experience experience = experienceRepo.findById(experienceId)
                .orElseThrow(() -> {
                    log.warn("Failed to delete experience: Experience with id {} not found.", experienceId);
                    return new ResourceNotFoundException("EXPERIENCE_NOT_FOUND");
                });
        experienceRepo.delete(experience);
        log.info("Experience successfully deleted.");
    }

    public ExperienceDto editExperience(HttpServletRequest request, Integer experienceId, ExperienceDto experienceDto) {
        Integer userId = extractUserIdFromToken(request);
        log.info("Operation of editing experience of the user is started....");
        Experience experience = experienceRepo.findById(experienceId)
                .orElseThrow(() -> {
                    log.warn("Failed to edit experience: Experience with id {} not found.", experienceId);
                    return new ResourceNotFoundException("EXPERIENCE_NOT_FOUND");
                });
        experienceMapper.mapToUpdate(experience, experienceDto);
        experienceRepo.save(experience);
        log.info("The process of editing user's experience successfully ended.");
        return experienceMapper.toDto(experience);
    }


    public List<ExperienceDto> getAllExperienceByUserId(Integer userId) {
        log.info("Process of getting another user's experience stared");
        if (!userRepo.existsById(userId)) {
            log.warn("User with id {} not found.", userId);
            throw new ResourceNotFoundException("User not found.");
        }
        List<ExperienceDto> experienceDtoList = experienceRepo.findAllByUserId(userId)
                .stream()
                .map(experienceMapper::toDto)
                .toList();
        log.info("Experience records for user with id {} returned", userId);
        return experienceDtoList;
    }

    private Integer extractUserIdFromToken(HttpServletRequest request) {
        return jwtUtil.getUserId(jwtUtil.resolveClaims(request));
    }
}
