package com.example._12_spring_intro_exer.services;

import com.example._12_spring_intro_exer.entities.Category;

import java.util.Set;

public interface CategoryService {

    Set<Category> getRandomCategories();
}
