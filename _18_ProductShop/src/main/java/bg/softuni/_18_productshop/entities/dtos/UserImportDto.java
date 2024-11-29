package bg.softuni._18_productshop.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class UserImportDto {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    @Length(min = 3)
    private String lastName;
    @JsonProperty("age")
    private int age;

    public UserImportDto() {}

    public UserImportDto(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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
}
