package com.example._exer_json.repositories;

import com.example._exer_json.models.dto.CategoryByProductsCountDTO;
import com.example._exer_json.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT NEW com.example._exer_json.models.dto.CategoryByProductsCountDTO(c.name, COUNT(p), AVG(p.price), SUM(p.price)) " +
            "FROM Category c JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY SIZE(c.products) ")
    List<CategoryByProductsCountDTO> findAllCategoriesOrderByProductCount();
}
