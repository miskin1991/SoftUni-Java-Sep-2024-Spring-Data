package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersDto {
    @JsonProperty
    int count;
    @JsonProperty
    List<UserProductsDto> userProductsDtos;

    public UsersDto() {}

    public UsersDto(int count, List<UserProductsDto> userProductsDtos) {
        this.count = count;
        this.userProductsDtos = userProductsDtos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserProductsDto> getUserProductsDtos() {
        return userProductsDtos;
    }

    public void setUserProductsDtos(List<UserProductsDto> userProductsDtos) {
        this.userProductsDtos = userProductsDtos;
    }
}
