package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.web.servlet.server.Jsp;

import java.math.BigDecimal;

public class ProductDto {
    @JsonProperty
    private String name;
    @JsonProperty
    private BigDecimal price;

    public ProductDto() {}

    public ProductDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
