package bg.softuni._18_productshop.services;

import bg.softuni._18_productshop.entities.User;
import bg.softuni._18_productshop.entities.dtos.*;
import bg.softuni._18_productshop.entities.interfaces.SellerIdByProductSoldCount;
import bg.softuni._18_productshop.entities.interfaces.SoldProduct;
import bg.softuni._18_productshop.repositories.ProductRepository;
import bg.softuni._18_productshop.repositories.UserRepository;
import bg.softuni._18_productshop.utils.ValidatorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String PATH = "src/main/resources/data/users.json";
    public static final String USERS_SOLD_PRODUCTS_JSON = "src/main/resources/data/users-sold-products.json";
    public static final String USERS_AND_PRODUCTS_JSON = "src/main/resources/data/users-and-products.json";
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper,
                           ObjectMapper objectMapper,
                           UserRepository userRepository,
                           ValidatorUtil validatorUtil,
                           ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException {
        UserImportDto[] userImportDtos = objectMapper.readValue(new File(PATH), UserImportDto[].class);
        List<User> users = new ArrayList<>();
        for (UserImportDto userImportDto : userImportDtos) {
            if (validatorUtil.isValid(userImportDto)) {
                users.add(this.modelMapper.map(userImportDto, User.class));
            } else {
                System.out.println(
                        validatorUtil.validate(userImportDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"))
                );
            }
        }
        userRepository.saveAll(users);
    }

    @Override
    public boolean isImported() {
        return userRepository.count() > 0;
    }

    @Override
    public void exportAllUsersFullNameWithAtLeastOneSoldItem() throws IOException {
        List<SellerIdByProductSoldCount> sellerIdByProductSoldCounts =
                productRepository.findSellerIdByProductSoldCounts(1L);
        List<UserFullNameAndSoldProductsDto> userFullNameAndSoldProductsDtos = new ArrayList<>();
        for (SellerIdByProductSoldCount s : sellerIdByProductSoldCounts) {
            List<SoldProduct> products = productRepository.findAllBySellerId(s.getSellerId());
            User user = userRepository.findById(s.getSellerId()).get();
            userFullNameAndSoldProductsDtos
                    .add(new UserFullNameAndSoldProductsDto(user.getFirstName(), user.getLastName(), products));
        }
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(USERS_SOLD_PRODUCTS_JSON), userFullNameAndSoldProductsDtos);
    }

    @Override
    public void exportAllUsersWithAtLeastOneSoldItem() throws IOException {
        List<Object[]> results = userRepository.getUsersWithProducts();

        Map<Long, UserProductsDto> map = new HashMap<>();

        for (Object[] result : results) {
            String productName = (String) result[0];
            BigDecimal price = (BigDecimal) result[1];
            Long id = (Long) result[2];
            String firstName = (String) result[3];
            String lastName = (String) result[4];
            int age = (int) result[5];

            if (map.containsKey(id)) {
                map.get(id)
                        .getSoldProducts()
                        .getProducts()
                        .add(new ProductDto(productName, price));
            } else {
                map.put(id,
                        new UserProductsDto(firstName, lastName, age,
                                new SoldProductsDto(0, new HashSet<>())));
                map.get(id).getSoldProducts().getProducts().add(new ProductDto(productName, price));
            }
        }

        map.forEach((aLong, userProductsDto) -> {
            int size = userProductsDto.getSoldProducts().getProducts().size();
            userProductsDto.getSoldProducts().setCount(size);
        });

        List<UserProductsDto> list = map.values()
                .stream()
                .sorted(Comparator.comparingInt(
                        (UserProductsDto user) -> user.getSoldProducts().getCount()).reversed())
                .toList();


        UsersDto usersDto = new UsersDto(list.size(), list);

        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(USERS_AND_PRODUCTS_JSON), usersDto);
    }
}
