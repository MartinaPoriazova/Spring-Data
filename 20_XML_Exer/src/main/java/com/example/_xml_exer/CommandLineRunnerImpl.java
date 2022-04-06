package com.example._xml_exer;

import com.example._xml_exer.model.dto.*;
import com.example._xml_exer.service.CategoryService;
import com.example._xml_exer.service.ProductService;
import com.example._xml_exer.service.UserService;
import com.example._xml_exer.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCES_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    private static final String USERS_FILE_NAME = "users.xml";

    private static final String OUTPUT_FILE_PATH = "src/main/resources/files/out/";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "products_in_range.xml";
    private static final String SOLD_PRODUCTS_FILE_NAME = "sold_products.xml";
    private static final String CATEGORY_BY_PRODUCTSCOUNT_FILE_NAME = "category_by_productscount.xml";
    private static final String USERS_WITH_MIN_ONE_PRODUCT_FILE_NAME = "users_with_min_one_product.xml";

    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter exercise: ");
        int exerNum = Integer.parseInt(bufferedReader.readLine());

        switch (exerNum) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> categoryByProductsCount();
            case 4 -> usersAndSoldProducts();
        }
//        TODO: No Car Dealer task
    }

    private void usersAndSoldProducts() throws JAXBException {
        UsersAndProductsViewRootDto rootDto = userService
                .findUsersWithMoreThanOneSoldProductOrderByProductSoldDescThenByLastName();

        xmlParser.writeToFile(OUTPUT_FILE_PATH + USERS_WITH_MIN_ONE_PRODUCT_FILE_NAME, rootDto);
    }

    private void categoryByProductsCount() throws JAXBException {
        CategoryViewRootDto rootDto = categoryService
                .findAllCategoriesByProductCount();

        xmlParser.writeToFile(OUTPUT_FILE_PATH + CATEGORY_BY_PRODUCTSCOUNT_FILE_NAME, rootDto);
    }

    private void soldProducts() throws JAXBException {
        UserViewRootDto rootDto = userService
                .findUsersWithMoreThanOneSoldProduct();

        xmlParser.writeToFile(OUTPUT_FILE_PATH + SOLD_PRODUCTS_FILE_NAME, rootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto = productService
                .findProductsInRangeWithoutBuyer();

        xmlParser.writeToFile(OUTPUT_FILE_PATH + PRODUCTS_IN_RANGE_FILE_NAME, rootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        categorySeedData();
        userSeedData();
        productSeedData();
    }

    private void productSeedData() throws JAXBException, FileNotFoundException {
        if (productService.getEntityCount() == 0) {
            ProductSeedRootDto productSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME, ProductSeedRootDto.class);

            productService.seedProducts(productSeedRootDto.getProducts());
        }
    }

    private void userSeedData() throws JAXBException, FileNotFoundException {
        if (userService.getEntityCount() == 0) {
            UserSeedRootDto userSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + USERS_FILE_NAME, UserSeedRootDto.class);

            userService.seedUsers(userSeedRootDto.getUsers());
        }
    }

    private void categorySeedData() throws JAXBException, FileNotFoundException {
        if (categoryService.getEntityCount() == 0) {
            CategorySeedRootDto categorySeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME, CategorySeedRootDto.class);

            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }
    }
}
