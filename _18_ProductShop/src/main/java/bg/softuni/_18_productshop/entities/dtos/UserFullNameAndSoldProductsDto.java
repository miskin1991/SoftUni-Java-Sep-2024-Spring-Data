package bg.softuni._18_productshop.entities.dtos;

import bg.softuni._18_productshop.entities.interfaces.SoldProduct;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserFullNameAndSoldProductsDto {
    @JsonProperty("fistName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("soldProducts")
    List<SoldProduct> soldProducts;

    public UserFullNameAndSoldProductsDto() {
        this.soldProducts = new ArrayList<>();
    }

    public UserFullNameAndSoldProductsDto(String firstName, String lastName, List<SoldProduct> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SoldProduct> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProduct> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
