package com.example._xml_exer.service;

import com.example._xml_exer.model.dto.ProductSeedDto;
import com.example._xml_exer.model.dto.ProductViewRootDto;

import java.util.List;

public interface ProductService {

    long getEntityCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findProductsInRangeWithoutBuyer();
}
