package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProductInRangeDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("seller")
    private String seller;

    public ProductInRangeDto() {}

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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
