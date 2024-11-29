package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class SoldProductsDto {
    @JsonProperty
    int count;
    @JsonProperty
    Set<ProductDto> products;

    public SoldProductsDto() {}

    public SoldProductsDto(int count, Set<ProductDto> products) {
        this.count = count;
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDto> products) {
        this.products = products;
    }
}
