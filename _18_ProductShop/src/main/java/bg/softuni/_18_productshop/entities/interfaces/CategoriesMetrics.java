package bg.softuni._18_productshop.entities.interfaces;

import java.math.BigDecimal;

public interface CategoriesMetrics {
    String getCategory();
    int getProductsCount();
    BigDecimal getAveragePrice();
    BigDecimal getTotalRevenue();
}
