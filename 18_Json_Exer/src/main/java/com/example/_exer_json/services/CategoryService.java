package com.example._exer_json.services;

import com.example._exer_json.models.dto.CategoryByProductsCountDTO;
import com.example._exer_json.models.entities.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> findRandomCategories();

    List<CategoryByProductsCountDTO> findAllCategoriesOrderByProductCount();
}
