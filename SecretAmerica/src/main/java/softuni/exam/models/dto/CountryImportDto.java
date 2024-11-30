package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public class CountryImportDto {
    @Expose
    @Length(min = 3, max = 40)
    private String name;
    @Min(value = 0)
    private double area;

    public @Length(min = 3, max = 40) String getName() {
        return name;
    }

    public void setName(@Length(min = 3, max = 40) String name) {
        this.name = name;
    }

    @Min(value = 0)
    public double getArea() {
        return area;
    }

    public void setArea(@Min(value = 0) double area) {
        this.area = area;
    }
}
