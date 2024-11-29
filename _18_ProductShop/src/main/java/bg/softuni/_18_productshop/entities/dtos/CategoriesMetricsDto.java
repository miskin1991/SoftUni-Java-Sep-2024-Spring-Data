package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoriesMetricsDto {
    @JsonProperty
    private String category;
    @JsonProperty
    private Long productsCount;
    @JsonProperty
    private Double averagePrice;
    @JsonProperty
    private Double totalRevenue;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Long productsCount) {
        this.productsCount = productsCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
