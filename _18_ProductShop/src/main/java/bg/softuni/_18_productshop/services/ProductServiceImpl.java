package bg.softuni._18_productshop.services;

import bg.softuni._18_productshop.entities.Category;
import bg.softuni._18_productshop.entities.Product;
import bg.softuni._18_productshop.entities.User;
import bg.softuni._18_productshop.entities.dtos.ProductInRangeDto;
import bg.softuni._18_productshop.entities.dtos.ProductImportDto;
import bg.softuni._18_productshop.repositories.CategoryRepository;
import bg.softuni._18_productshop.repositories.ProductRepository;
import bg.softuni._18_productshop.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PATH = "src/main/resources/data/products.json";
    public static final String PRODUCT_IN_RANGE_JSON = "src/main/resources/data/product-in-range.json";
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper,
                              ObjectMapper objectMapper,
                              ProductRepository productRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedProducts() throws IOException {
        ProductImportDto[] productImportDtos =
                objectMapper.readValue(new File(PATH), ProductImportDto[].class);

        List<Product> products = Arrays.stream(productImportDtos)
                .map(dto -> {
                    Product map = this.modelMapper.map(dto, Product.class);
                    map.setSeller(getRandomUser());
                    map.setBuyer(getRandomUser());
                    map.setCategories(getRandomCategories());
                    return map;
                })
                .toList();
        productRepository.saveAll(products);
    }

    private Set<Category> getRandomCategories() {
        int count = ThreadLocalRandom.current().nextInt(1, 4);
        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            categories.add(
                    categoryRepository
                            .findById(ThreadLocalRandom
                                    .current()
                                    .nextLong(1L, categoryRepository.count()) + 1L).get());
        }
        return categories;
    }

    private User getRandomUser() {
        return userRepository
                .findById(ThreadLocalRandom
                        .current()
                        .nextLong(1L, userRepository.count()) + 1L).get();
    }

    @Override
    public boolean isImported() {
        return productRepository.count() > 0;
    }

    @Override
    public void exportNotSoldProductByPriceRange(BigDecimal lower, BigDecimal upper) throws IOException {
        List<Product> notSoldProductWithPriceBetween =
                productRepository.getNotSoldProductWithPriceBetween(lower, upper);

        List<ProductInRangeDto> list = notSoldProductWithPriceBetween
                .stream()
                .map(product -> modelMapper.map(product, ProductInRangeDto.class))
                .toList();
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(PRODUCT_IN_RANGE_JSON), list);
    }
}
