package bg.softuni._18_productshop.controllers;

import bg.softuni._18_productshop.services.CategoryService;
import bg.softuni._18_productshop.services.ProductService;
import bg.softuni._18_productshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class CommandLineController implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CommandLineController(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!categoryService.isImported()) {
            categoryService.seedCategories();
        }
        if (!userService.isImported()) {
            userService.seedUsers();
        }
        if (!productService.isImported()) {
            productService.seedProducts();
        }

//        exportNotSoldProductByPriceRange(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));
//        exportAllUsersFullNameWithAtLeastOneSoldItem();
//        exportAllCategoriesByProducts();
        exportAllUsersWithAtLeastOneSoldItem();
    }

    private void exportAllUsersWithAtLeastOneSoldItem() throws IOException {
         userService.exportAllUsersWithAtLeastOneSoldItem();
    }

    private void exportAllCategoriesByProducts() throws IOException {
        categoryService.exportAllCategoriesByProducts();
    }

    private void exportAllUsersFullNameWithAtLeastOneSoldItem() throws IOException {
        userService.exportAllUsersFullNameWithAtLeastOneSoldItem();
    }

    private void exportNotSoldProductByPriceRange(BigDecimal lower, BigDecimal upper) throws IOException {
        productService.exportNotSoldProductByPriceRange(lower, upper);
    }
}
