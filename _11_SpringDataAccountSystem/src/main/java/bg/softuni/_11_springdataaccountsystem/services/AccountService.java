package bg.softuni._11_springdataaccountsystem.services;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal amount, Long id);
    void transferMoney(BigDecimal amount, Long id);
}
