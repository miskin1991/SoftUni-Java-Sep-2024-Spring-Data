package bg.softuni._12_springdatabookshopsystem.services;

import bg.softuni._12_springdatabookshopsystem.entities.Category;
import bg.softuni._12_springdatabookshopsystem.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getRandomCategory() {
        long count = categoryRepository.count();
        long randomId = (long) ((Math.random() * count) + 1);
        return new HashSet<>() {{
            add(categoryRepository.getReferenceById(randomId));
        }};
    }

    @Override
    public void storeCategory(Category category) {
        if (category.getName() == null
                || category.getName().isBlank() || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        } else {
            categoryRepository.save(category);
        }
    }
}
