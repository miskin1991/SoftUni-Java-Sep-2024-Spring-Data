package bg.softuni._12_springdatabookshopsystem.services;

import bg.softuni._12_springdatabookshopsystem.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategory();
    void storeCategory(Category category);
}
