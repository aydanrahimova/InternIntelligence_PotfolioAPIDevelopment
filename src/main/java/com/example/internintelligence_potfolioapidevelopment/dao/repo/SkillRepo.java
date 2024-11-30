package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepo extends JpaRepository<Skill,Integer> {
    List<Skill> findAllByUserId(Integer userId);

}
