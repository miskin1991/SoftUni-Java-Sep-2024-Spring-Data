package bg.softuni._11_springdataaccountsystem.services;

import bg.softuni._11_springdataaccountsystem.models.Account;
import bg.softuni._11_springdataaccountsystem.models.User;
import bg.softuni._11_springdataaccountsystem.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        User user = account.getUser();
        if (user == null) {
            throw new RuntimeException("Account does not have a User id");
        }

        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        } else {
            account.setBalance(balance.subtract(amount));
            accountRepository.save(account);
        }
    }

    @Override
    public void transferMoney(BigDecimal amount, Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        User user = account.getUser();
        if (user == null) {
            throw new RuntimeException("Account does not have a User id");
        }

        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        } else if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient amount");
        } else {
            account.setBalance(balance.subtract(amount));
            accountRepository.save(account);
        }
    }
}
