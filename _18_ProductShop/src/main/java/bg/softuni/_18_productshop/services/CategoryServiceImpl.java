package bg.softuni._18_productshop.services;

import bg.softuni._18_productshop.entities.Category;
import bg.softuni._18_productshop.entities.dtos.CategoriesMetricsDto;
import bg.softuni._18_productshop.entities.dtos.CategoryImportDto;
import bg.softuni._18_productshop.entities.interfaces.CategoriesMetrics;
import bg.softuni._18_productshop.repositories.CategoryRepository;
import bg.softuni._18_productshop.utils.ValidatorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String PATH = "src/main/resources/data/categories.json";
    public static final String CATEGORIES_BY_PRODUCTS_JSON = "src/main/resources/data/categories-by-products.json";
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper,
                               ObjectMapper objectMapper,
                               ValidatorUtil validatorUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        CategoryImportDto[] categoryImportDtos = objectMapper.readValue(new File(PATH), CategoryImportDto[].class);
        List<Category> categories = new ArrayList<>();
        for (CategoryImportDto categoryImportDto : categoryImportDtos) {
            if (this.validatorUtil.isValid(categoryImportDto)) {
                categories.add(this.modelMapper.map(categoryImportDto, Category.class));
            } else {
                System.out.println(
                        validatorUtil.validate(categoryImportDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n")));
            }
        }
        categoryRepository.saveAll(categories);
    }

    @Override
    public boolean isImported() {
        return categoryRepository.count() > 0;
    }

    @Override
    public void exportAllCategoriesByProducts() throws IOException {
        List<CategoriesMetrics> metrics = categoryRepository.getAllCategoriesMetrics();
        CategoriesMetricsDto[] map = modelMapper.map(metrics, CategoriesMetricsDto[].class);
        objectMapper.
                writerWithDefaultPrettyPrinter().
                writeValue(new File(CATEGORIES_BY_PRODUCTS_JSON), map);
    }
}
