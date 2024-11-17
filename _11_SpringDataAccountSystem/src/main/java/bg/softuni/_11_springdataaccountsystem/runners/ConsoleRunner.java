package bg.softuni._11_springdataaccountsystem.runners;

import bg.softuni._11_springdataaccountsystem.models.Account;
import bg.softuni._11_springdataaccountsystem.models.User;
import bg.softuni._11_springdataaccountsystem.services.AccountService;
import bg.softuni._11_springdataaccountsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User("Pesho", 20);
        Account account = new Account(user, new BigDecimal("25000"));
        user.setAccounts(new HashSet<>() {{
            add(account);
        }});
        userService.registerUser(user);

        accountService.withdrawMoney(new BigDecimal("20000"), 1L);
        accountService.transferMoney(new BigDecimal("1000"), 1L);
    }
}
