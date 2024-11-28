package com.example.internintelligence_potfolioapidevelopment.service;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Skill;
import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.SkillRepo;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.UserRepo;
import com.example.internintelligence_potfolioapidevelopment.dto.SkillDto;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_potfolioapidevelopment.mapper.SkillMapper;
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
public class SkillService {
    private final SkillRepo skillRepo;
    private final UserRepo userRepo;
    private final SkillMapper skillMapper;
    private final JwtUtil jwtUtil;

    public List<SkillDto> getOwnSkills(HttpServletRequest request) {
        log.info("Operation of getting skills of the authenticated user started...");
        Long userId = extractUserIdFromToken(request);
        return skillRepo.findAllByUserId(userId).stream()
                .map(skillMapper::toDto)
                .toList();
    }

    public SkillDto addSkill(HttpServletRequest request, SkillDto skillDto) {
        Long userId = extractUserIdFromToken(request);
        log.info("Adding new skill for user with id {}...", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} not found.", userId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        Skill skill = skillMapper.toEntity(skillDto);
        skill.setUser(user);
        skillRepo.save(skill);
        log.info("New skill added for user with id {}", userId);
        return skillMapper.toDto(skill);
    }

    public void deleteSkill(HttpServletRequest request, Long skillId) {
        Long userId = extractUserIdFromToken(request);
        log.info("Deleting skill with id {} for user with id {}...", skillId, userId);
        Skill skill = skillRepo.findById(skillId).orElseThrow(() -> {
            log.warn("Failed to delete skill: Skill with id {} not found.", skillId);
            return new ResourceNotFoundException("SKILL_NOT_FOUND");
        });
        skillRepo.delete(skill);
        log.info("Skill deleted successfully for user with id {}", userId);
    }

    public SkillDto editSkill(HttpServletRequest request, Long skillId, SkillDto skillDto) {
        Long userId = extractUserIdFromToken(request);
        log.info("Editing skill with id {} for user with id {}...", skillId, userId);
        Skill skill = skillRepo.findById(skillId).orElseThrow(() -> {
            log.warn("Failed to edit skill: Skill with id {} not found.", skillId);
            return new ResourceNotFoundException("SKILL_NOT_FOUND");
        });
        skillMapper.mapToUpdate(skill, skillDto);
        skillRepo.save(skill);
        log.info("Skill with id {} successfully updated.", skillId);
        return skillMapper.toDto(skill);
    }

    private Long extractUserIdFromToken(HttpServletRequest request) {
        return jwtUtil.getUserId(jwtUtil.resolveClaims(request));
    }

    public List<SkillDto> getAllSkillsByUserId(Long userId) {
        log.info("Process of getting another user's skills stared");
        if (!userRepo.existsById(userId)) {
            log.warn("Failed to get skills: User with id {} not found.", userId);
            throw new ResourceNotFoundException("User not found.");
        }
        List<SkillDto> skillDtoList = skillRepo.findAllByUserId(userId)
                .stream()
                .map(skillMapper::toDto)
                .toList();
        log.info("Skills for user with id {} returned", userId);
        return skillDtoList;
    }
}
