package com.example.advquerying;

import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    private final ShampooRepository shampooRepository;
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public Runner(ShampooRepository shampooRepository, ShampooService shampooService, IngredientService ingredientService) {
        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

//        TODO:change methods and services!

        System.out.println(this.shampooService.countPriceLowerThan(BigDecimal.valueOf(8.50)));

//        this.ingredientService.selectInNames(List.of("Lavender", "Herbs", "Apple"))
//                .forEach(System.out::println);
    }

}




// TODO: Other examples, not included in the homework :)

//      Examples:
//    If called directly from Repos:

//        public void run(String... args) {
//            Scanner scanner = new Scanner(System.in);
//
//        _demo_findByrBrand(scanner, shampooRepository);
//        _01_findBySize(scanner, shampooRepository);
//        _07_findByIngredients(scanner, shampooRepository);
//    }

//    private void _07_findByIngredients(Scanner scanner, ShampooRepository shampooRepository) {
//        String firstIngredient = scanner.nextLine();
//        String secondIngredient = scanner.nextLine();
//        Set<String> ingredientsNames = Set.of(firstIngredient, secondIngredient);
//
//        this.shampooRepository.findByIngredientsNames(ingredientsNames)
//                .forEach(System.out::println);
//    }
//
//    private void _01_findBySize(Scanner scanner, ShampooRepository shampooRepository) {
//        String sizeName = scanner.nextLine();
//        Size size = Size.valueOf(sizeName);
//        this.shampooRepository.findBySizeOrderById(size)
//                .forEach(System.out::println);
//    }
//
//    private void _demo_findByrBrand(Scanner scanner, ShampooRepository shampooRepository) {
//        String brand = scanner.nextLine();
//        this.shampooRepository.findAllByBrand(brand)
//                .forEach(s -> System.out.println(s.getId()));
//    }

