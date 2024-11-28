package com.example.internintelligence_potfolioapidevelopment.service;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Education;
import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.EducationRepo;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.UserRepo;
import com.example.internintelligence_potfolioapidevelopment.dto.EducationDto;
import com.example.internintelligence_potfolioapidevelopment.exception.ForbiddenException;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_potfolioapidevelopment.mapper.EducationMapper;
import com.example.internintelligence_potfolioapidevelopment.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EducationService {
    private final EducationRepo educationRepo;
    private final EducationMapper educationMapper;
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;

    public List<EducationDto> getAllOwnEducation(HttpServletRequest request) {
        log.info("Operation of getting education of the user is started...");
        Long userId = extractUserIdFromToken(request);
        log.info("All education of user is returned.");
        return educationRepo.findAllByUserId(userId)
                .stream()
                .map(educationMapper::toDto)
                .toList();
    }

    public void deleteEducationOfUser(HttpServletRequest request, Long educationId) {
        log.info("Deleting education with id {} started...", educationId);
        Long userId = extractUserIdFromToken(request);
        Education education = educationRepo.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education with ID " + educationId + " not found."));
        if (!education.getUser().getId().equals(userId)) {
            log.error("User with ID {} is not authorized to delete education with ID {}", userId, educationId);
            throw new ForbiddenException("You are not authorized to delete this education.");
        }
        educationRepo.deleteById(educationId);
        log.info("Education with ID {} deleted successfully by user with ID {}", educationId, userId);
    }

    public EducationDto addEducation(HttpServletRequest request, EducationDto dto) {
        log.info("Operation of adding new education to user is started...");
        Long id = extractUserIdFromToken(request);
        log.info("Attempting to add new education to user with id {}", id);
        User user = userRepo.findById(id).orElseThrow(() -> {
            log.warn("Failed to add new education: User with id {} not found", id);
            return new ResourceNotFoundException("User with id " + id + " not found.");
        });
        Education education = educationMapper.toEntity(dto);
        education.setUser(user);
        educationRepo.save(education);
        log.info("New education is added to user with id {}", id);
        return educationMapper.toDto(education);
    }

    public EducationDto editEducationOfUser(HttpServletRequest request, Long educationId, EducationDto educationDto) {
        log.info("Editing education with id {} started...", educationId);
        Long userId = extractUserIdFromToken(request);
        Education education = educationRepo.findById(educationId).orElseThrow(() -> {
            log.warn("Failed to edit education: Education with id {} not found", educationId);
            return new ResourceNotFoundException("Education not found");
        });
        if (!education.getUser().getId().equals(userId)) {
            log.error("Failed to edit education: User with id {} is not authorized to delete this education", userId);
            throw new ForbiddenException("You are not authorized to delete this education.");
        }
        educationMapper.mapForUpdate(education, educationDto);
        educationRepo.save(education);
        log.info("Education successfully edited.");
        return educationMapper.toDto(education);
    }

    private Long extractUserIdFromToken(HttpServletRequest request) {
        return jwtUtil.getUserId(jwtUtil.resolveClaims(request));
    }


    public List<EducationDto> getAllEducationByUserId(Long userId) {
        log.info("Getting all education of the user with id {} started", userId);
        if (!userRepo.existsById(userId)) {
            log.info("Failed to get education list: User with id {} not found", userId);
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
        List<EducationDto> educationDtoList = educationRepo.findAllByUserId(userId)
                .stream()
                .map(educationMapper::toDto)
                .toList();
        log.info("Education records for user with id {} returned.", userId);
        return educationDtoList;
    }

}
