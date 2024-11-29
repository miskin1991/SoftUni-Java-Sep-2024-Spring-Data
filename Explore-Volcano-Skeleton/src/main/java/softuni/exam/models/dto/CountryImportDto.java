package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

public class CountryImportDto {
    @Length(min = 3, max = 30)
    private String name;
    @Length(min = 3, max = 30)
    private String capital;

    public CountryImportDto() {}

    public @Length(min = 3, max = 30) String getName() {
        return name;
    }

    public void setName(@Length(min = 3, max = 30) String name) {
        this.name = name;
    }

    public @Length(min = 3, max = 30) String getCapital() {
        return capital;
    }

    public void setCapital(@Length(min = 3, max = 30) String capital) {
        this.capital = capital;
    }
}
