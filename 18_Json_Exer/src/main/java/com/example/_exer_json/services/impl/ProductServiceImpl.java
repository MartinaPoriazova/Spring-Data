package com.example._exer_json.services.impl;

import com.example._exer_json.constants.GlobalConstant;
import com.example._exer_json.models.dto.ProductNameAndPriceDTO;
import com.example._exer_json.models.dto.ProductSeedDTO;
import com.example._exer_json.models.entities.Product;
import com.example._exer_json.repositories.ProductRepository;
import com.example._exer_json.services.CategoryService;
import com.example._exer_json.services.ProductService;
import com.example._exer_json.services.UserService;
import com.example._exer_json.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_FILE_NAME = "products.json";

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() == 0) {
            Arrays.stream(gson.fromJson(
                            Files.readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + PRODUCTS_FILE_NAME)),
                            ProductSeedDTO[].class))
                    .filter(validationUtil::isValid)
                    .map(productSeedDTO -> {
                        Product product = modelMapper.map(productSeedDTO, Product.class);
                        product.setSeller(userService.findRandomUser());
                        if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                            product.setBuyer(userService.findRandomUser());
                        }
                        product.setCategories(categoryService.findRandomCategories());
                        return product;
                    })
                    .forEach(productRepository::save);
        }
    }

    @Override
    public List<ProductNameAndPriceDTO> findAllProductsInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
        return productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower, upper)
                .stream()
                .map(product -> {
                    ProductNameAndPriceDTO productNameAndPriceDTO = modelMapper
                            .map(product, ProductNameAndPriceDTO.class);

                    productNameAndPriceDTO.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDTO;
                })
                .collect(Collectors.toList());
    }
}
