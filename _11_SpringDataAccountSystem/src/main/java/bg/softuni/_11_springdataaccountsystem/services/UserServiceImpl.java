package bg.softuni._11_springdataaccountsystem.services;

import bg.softuni._11_springdataaccountsystem.models.User;
import bg.softuni._11_springdataaccountsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new RuntimeException("User already exists!");
        } else {
            userRepository.save(user);
        }
    }
}
