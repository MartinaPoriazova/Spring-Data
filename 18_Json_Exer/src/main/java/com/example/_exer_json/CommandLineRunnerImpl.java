package com.example._exer_json;

import com.example._exer_json.car_dealer.services.*;
import com.example._exer_json.models.dto.CategoryByProductsCountDTO;
import com.example._exer_json.models.dto.ProductNameAndPriceDTO;
import com.example._exer_json.models.dto.UserSoldDTO;
import com.example._exer_json.models.dto.UserWithMinOneSoldProductDTO;
import com.example._exer_json.services.CategoryService;
import com.example._exer_json.services.ProductService;
import com.example._exer_json.services.UserService;
import com.google.gson.Gson;
import com.sun.xml.bind.v2.TODO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_PATH = "src/main/resources/files/out/";
    private static final String PRODUCT_IN_RANGE_FILE_NAME = "products_in_range.json";
    private static final String SOLD_PRODUCTS_FILE_NAME = "sold_products.json";
    private static final String CATEGORY_BY_PRODUCTSCOUNT_FILE_NAME = "category_by_productscount.json";
    private static final String USERS_WITH_MIN_ONE_PRODUCT_FILE_NAME = "users_with_min_one_product.json";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SaleService saleService;
    private final SupplierService supplierService;

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public CommandLineRunnerImpl(CustomerService customerService, CarService carService, PartService partService, SaleService saleService, SupplierService supplierService, CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.customerService = customerService;
        this.carService = carService;
        this.partService = partService;
        this.saleService = saleService;
        this.supplierService = supplierService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        //        TODO: no package for product_shop, do not have the queries and some checkes of car_dealer (task 6)

        seedData();

        System.out.println("Enter exercise: ");
        int exerNum = Integer.parseInt(bufferedReader.readLine());

        switch (exerNum) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> categoryByProductsCount();
            case 4 -> usersAndSoldProducts();
        }
    }

    private void usersAndSoldProducts() throws IOException {
        List<UserWithMinOneSoldProductDTO> userWithMinOneSoldProductDTOS = userService
                .findAllUsersWithMinOneSoldProduct();

        String content = gson.toJson(userWithMinOneSoldProductDTOS);

        writeToFile(OUTPUT_PATH + USERS_WITH_MIN_ONE_PRODUCT_FILE_NAME, content);
    }

    private void categoryByProductsCount() throws IOException {
        List<CategoryByProductsCountDTO> categoryByProductsCountDTOS = categoryService
                .findAllCategoriesOrderByProductCount();

        String content = gson.toJson(categoryByProductsCountDTOS);

        writeToFile(OUTPUT_PATH + CATEGORY_BY_PRODUCTSCOUNT_FILE_NAME, content);
    }

    private void soldProducts() throws IOException {
        List<UserSoldDTO> userSoldDTOS = userService
                .findAllUsersWithMoreThanOneSoldProducts();

        String content = gson.toJson(userSoldDTOS);

        System.out.println();

        writeToFile(OUTPUT_PATH + SOLD_PRODUCTS_FILE_NAME, content);
    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDTO> productsDTOS = productService
                .findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        String content = gson.toJson(productsDTOS);

        writeToFile(OUTPUT_PATH + PRODUCT_IN_RANGE_FILE_NAME, content);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files
                .write(Path.of(filePath),
                        Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomers();
        saleService.seedSales();

    }
}
