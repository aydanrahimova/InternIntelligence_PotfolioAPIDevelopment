package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Skill;
import com.example.internintelligence_potfolioapidevelopment.dto.SkillDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SkillRepo extends JpaRepository<Skill,Long> {
    List<Skill> findAllByUserId(Long userId);

    List<Skill> findByUserId(Long id);
}
