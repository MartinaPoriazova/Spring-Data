package com.example._12_spring_intro_exer.repositories;

import com.example._12_spring_intro_exer.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
