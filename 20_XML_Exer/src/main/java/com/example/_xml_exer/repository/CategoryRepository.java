package com.example._xml_exer.repository;

import com.example._xml_exer.model.dto.CategoryWithProductsStatDto;
import com.example._xml_exer.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT NEW com.example._xml_exer.model.dto.CategoryWithProductsStatDto(c.name, COUNT(p), AVG(p.price), SUM(p.price)) " +
            "FROM Category c JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY SIZE(c.products) DESC")
    List<CategoryWithProductsStatDto> findAllCategoriesOrderByProductCount();
}
