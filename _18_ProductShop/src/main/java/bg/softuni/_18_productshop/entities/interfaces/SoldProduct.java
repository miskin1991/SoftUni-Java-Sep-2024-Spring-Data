package bg.softuni._18_productshop.entities.interfaces;

import java.math.BigDecimal;

public interface SoldProduct {
    String getName();
    BigDecimal getPrice();
    String getBuyerFirstName();
    String getBuyerLastName();
}
