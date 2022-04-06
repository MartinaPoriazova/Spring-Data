package com.example._exer_json.repositories;


import com.example._exer_json.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("SELECT new com.example._exer_json.models.dto.ProductWithBuyerDTO(" +
//            " p.name, p.price, p.seller.firstName, p.seller.lastName)" +
//            " FROM Product p" +
//            " WHERE p.price > :rangeStart AND p.price < :rangeEnd AND p.buyer IS NULL" +
//            " ORDER BY p.price ASC")
    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal lower, BigDecimal upper);


}
