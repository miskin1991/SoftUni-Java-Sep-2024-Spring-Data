package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProductsDto {
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private int age;
    @JsonProperty
    SoldProductsDto soldProductsDto;

    public UserProductsDto() {}

    public UserProductsDto(String firstName, String lastName, int age, SoldProductsDto soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProductsDto = soldProducts;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SoldProductsDto getSoldProducts() {
        return soldProductsDto;
    }

    public void setSoldProducts(SoldProductsDto soldProducts) {
        this.soldProductsDto = soldProducts;
    }
}
