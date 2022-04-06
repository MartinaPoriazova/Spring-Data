package com.example._xml_exer.service.impl;

import com.example._xml_exer.model.dto.CategorySeedDto;
import com.example._xml_exer.model.dto.CategoryViewRootDto;
import com.example._xml_exer.model.dto.CategoryWithProductsStatDto;
import com.example._xml_exer.model.entity.Category;
import com.example._xml_exer.repository.CategoryRepository;
import com.example._xml_exer.service.CategoryService;
import com.example._xml_exer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public long getEntityCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < 2; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, categoryRepository.count());

            categories.add(categoryRepository
                    .findById(randomId)
                    .orElse(null));
        }
        return categories;
    }

    @Override
    public CategoryViewRootDto findAllCategoriesByProductCount() {
        CategoryViewRootDto categoryViewRootDto = new CategoryViewRootDto();

        categoryViewRootDto.setCategories(categoryRepository
                .findAllCategoriesOrderByProductCount()
                .stream()
                .map(category -> modelMapper.map(category, CategoryWithProductsStatDto.class))
                .collect(Collectors.toList()));

        return categoryViewRootDto;
    }
}
