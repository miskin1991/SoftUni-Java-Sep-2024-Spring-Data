package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class CategoryImportDto {
    @JsonProperty("name")
    @Length(min = 3, max = 15)
    private String name;

    public CategoryImportDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
