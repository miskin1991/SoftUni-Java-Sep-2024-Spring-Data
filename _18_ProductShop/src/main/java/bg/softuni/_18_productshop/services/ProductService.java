package bg.softuni._18_productshop.services;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    void seedProducts() throws IOException;
    boolean isImported();

    void exportNotSoldProductByPriceRange(BigDecimal lower, BigDecimal upper) throws IOException;
}
