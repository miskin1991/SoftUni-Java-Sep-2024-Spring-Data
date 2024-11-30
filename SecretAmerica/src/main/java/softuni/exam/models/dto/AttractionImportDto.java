package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public class AttractionImportDto {
    @Expose
    @Length(min = 5, max = 40)
    private String name;
    @Expose
    @Length(min = 10, max = 100)
    private String description;
    @Expose
    @Length(min = 3, max = 30)
    private String type;
    @Expose
    @Min(value = 0)
    private double elevation;
    @Expose
    private int country;

    public @Length(min = 5, max = 40) String getName() {
        return name;
    }

    public void setName(@Length(min = 5, max = 40) String name) {
        this.name = name;
    }

    public @Length(min = 10, max = 100) String getDescription() {
        return description;
    }

    public void setDescription(@Length(min = 10, max = 100) String description) {
        this.description = description;
    }

    public @Length(min = 3, max = 30) String getType() {
        return type;
    }

    public void setType(@Length(min = 3, max = 30) String type) {
        this.type = type;
    }

    @Min(value = 0)
    public double getElevation() {
        return elevation;
    }

    public void setElevation(@Min(value = 0) double elevation) {
        this.elevation = elevation;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }
}


