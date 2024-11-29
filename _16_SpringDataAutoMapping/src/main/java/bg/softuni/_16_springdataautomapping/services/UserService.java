package bg.softuni._16_springdataautomapping.services;


import bg.softuni._16_springdataautomapping.entities.dtos.UserLoginDto;
import bg.softuni._16_springdataautomapping.entities.dtos.userRegisterDto;

public interface UserService {
    String registerUser(userRegisterDto user);

    String loginUser(UserLoginDto userLoginDto);

    String logout();
}
