package com.example._exer_json.services.impl;

import com.example._exer_json.constants.GlobalConstant;
import com.example._exer_json.models.dto.CategoryByProductsCountDTO;
import com.example._exer_json.models.dto.CategorySeedDTO;
import com.example._exer_json.models.entities.Category;
import com.example._exer_json.repositories.CategoryRepository;
import com.example._exer_json.services.CategoryService;
import com.example._exer_json.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_NAME = "categories.json";

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDTO[] categorySeedDTOS = gson
                .fromJson(fileContent, CategorySeedDTO[].class);

        Arrays.stream(categorySeedDTOS)
                .filter(validationUtil::isValid)
                .map(categorySeedDTO -> modelMapper.map(categorySeedDTO, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categorySet = new HashSet<>();
        int catCount = ThreadLocalRandom.current().nextInt(1, 3);
        long totalCategoriesCount = categoryRepository.count();

        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalCategoriesCount + 1);

            categorySet.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categorySet;
    }

    @Override
    public List<CategoryByProductsCountDTO> findAllCategoriesOrderByProductCount() {
        return categoryRepository
                .findAllCategoriesOrderByProductCount()
                .stream()
                .map(category -> modelMapper.map(category, CategoryByProductsCountDTO.class))
                .collect(Collectors.toList());
    }
}
