package com.example._xml_exer.service;


import com.example._xml_exer.model.dto.CategorySeedDto;
import com.example._xml_exer.model.dto.CategoryViewRootDto;
import com.example._xml_exer.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories(List<CategorySeedDto> categories);

    long getEntityCount();

    Set<Category> getRandomCategories();

    CategoryViewRootDto findAllCategoriesByProductCount();
}
