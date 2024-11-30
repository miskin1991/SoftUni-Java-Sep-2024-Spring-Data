package softuni.exam.models.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hibernate.validator.constraints.Length;

/*
<visitors>
    <visitor>
        <first_name>John</first_name>
        <last_name>Smith</last_name>
        <attraction_id>12</attraction_id>
        <country_id>73</country_id>
        <personal_data_id>61</personal_data_id>
    </visitor>
</visitors>
 */
public class VisitorImportDto {

    @JacksonXmlProperty(localName = "first_name")
    @Length(min = 2, max = 20)
    private String firstName;

    @JacksonXmlProperty(localName = "last_name")
    @Length(min = 2, max = 20)
    private String lastName;

    @JacksonXmlProperty(localName = "attraction_id")
    private int attraction;

    @JacksonXmlProperty(localName = "country_id")
    private int country;

    @JacksonXmlProperty(localName = "personal_data_id")
    private int personalData;

    public @Length(min = 2, max = 20) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Length(min = 2, max = 20) String firstName) {
        this.firstName = firstName;
    }

    public @Length(min = 2, max = 20) String getLastName() {
        return lastName;
    }

    public void setLastName(@Length(min = 2, max = 20) String lastName) {
        this.lastName = lastName;
    }

    public int getAttraction() {
        return attraction;
    }

    public void setAttraction(int attraction) {
        this.attraction = attraction;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getPersonalData() {
        return personalData;
    }

    public void setPersonalData(int personalData) {
        this.personalData = personalData;
    }
}
