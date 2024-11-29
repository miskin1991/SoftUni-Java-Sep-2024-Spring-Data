package bg.softuni._16_springdataautomapping.services;

import bg.softuni._16_springdataautomapping.entities.User;
import bg.softuni._16_springdataautomapping.entities.dtos.UserLoginDto;
import bg.softuni._16_springdataautomapping.entities.dtos.userRegisterDto;
import bg.softuni._16_springdataautomapping.repositories.UserRepository;
import bg.softuni._16_springdataautomapping.utils.ValidatorUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private User userInstance;

    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userInstance = null;
    }

    @Override
    public String registerUser(userRegisterDto userRegisterDto) {

        if (!validatorUtil.isValid(userRegisterDto))
        {
            return validatorUtil
                    .validate(userRegisterDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            return "Passwords do not match";
        }

        User user = modelMapper.map(userRegisterDto, User.class);
        setAdminRole(user);

        userRepository.save(user);

        return String.format("%s was registered", userRegisterDto.getFullName());
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        if (userInstance != null) {
            return "User already logged in";
        }

        Optional<User> user = userRepository
                .findUserByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());

        if (user.isEmpty()) {
            return "Invalid email or password";
        } else {
            setUserInstance(user.get());
        }

        return "Successfully logged in %s".formatted(getUserInstance().getFullName());
    }

    @Override
    public String logout() {
        String output;
        if (userInstance == null) {
            output = "Cannot log out. No user was logged in.";
        } else {
            output = "User %s successfully logged out.".formatted(getUserInstance().getFullName());
            userInstance = null;
        }
        return output;
    }

    private void setAdminRole(User user) {
        if (userRepository.count() == 0) {
            user.setAdmin(true);
        }
    }

    public User getUserInstance() {
        return userInstance;
    }

    public void setUserInstance(User userInstance) {
        this.userInstance = userInstance;
    }
}
