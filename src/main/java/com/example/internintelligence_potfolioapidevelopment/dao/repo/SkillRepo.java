package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Skill;
import com.example.internintelligence_potfolioapidevelopment.dto.SkillDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SkillRepo extends JpaRepository<Skill,Integer> {
    List<Skill> findAllByUserId(Integer userId);

    List<Skill> findByUserId(Integer id);
}
