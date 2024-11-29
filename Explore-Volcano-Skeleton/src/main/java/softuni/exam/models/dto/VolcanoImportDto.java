package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.enums.VolcanoType;

import javax.validation.constraints.Min;

public class VolcanoImportDto {
    @Length(min = 2, max = 30)
    private String name;
    @Min(value = 1)
    private int elevation;

    private VolcanoType volcanoType;

    private boolean isActive;

    private String lastEruption;

    private int country;

    public @Length(min = 2, max = 30) String getName() {
        return name;
    }

    public void setName(@Length(min = 2, max = 30) String name) {
        this.name = name;
    }

    @Min(value = 1)
    public int getElevation() {
        return elevation;
    }

    public void setElevation(@Min(value = 1) int elevation) {
        this.elevation = elevation;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }
}