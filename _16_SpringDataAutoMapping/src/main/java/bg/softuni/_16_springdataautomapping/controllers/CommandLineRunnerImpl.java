package bg.softuni._16_springdataautomapping.controllers;

import bg.softuni._16_springdataautomapping.entities.dtos.UserLoginDto;
import bg.softuni._16_springdataautomapping.entities.dtos.userRegisterDto;
import bg.softuni._16_springdataautomapping.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;

    @Autowired
    public CommandLineRunnerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input;
        String output;
        while (!"End".equals(input = scanner.nextLine())) {
            String[] tokens = input.split("\\|");
            switch (tokens[0]) {
                case "RegisterUser":
                    output = userService.registerUser(new userRegisterDto(tokens[1], tokens[2], tokens[3], tokens[4]));
                    break;
                case "LoginUser":
                    output = userService.loginUser(new UserLoginDto(tokens[1], tokens[2]));
                    break;
                case "Logout":
                    output = userService.logout();
                    break;
                default:
                    output = "Invalid input";
                    break;
            }

            System.out.println(output);
        }
    }
}
