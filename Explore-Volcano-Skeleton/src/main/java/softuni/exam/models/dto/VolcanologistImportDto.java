package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "volcanologist")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistImportDto {
    @XmlElement(name = "first_name")
    @Length(min = 2, max = 30)
    private String firstName;
    @XmlElement(name = "last_name")
    @Length(min = 2, max = 30)
    private String lastName;
    @XmlElement
    private double salary;
    @XmlElement
    @Min(18)
    @Max(80)
    private int age;
    @XmlElement(name = "exploring_from")
    private String exploringFrom;
    @XmlElement(name = "exploring_volcano_id")
    private int exploringVolcanoId;

    public VolcanologistImportDto() {}

    public @Length(min = 2, max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Length(min = 2, max = 30) String firstName) {
        this.firstName = firstName;
    }

    public @Length(min = 2, max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@Length(min = 2, max = 30) String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Min(18)
    @Max(80)
    public int getAge() {
        return age;
    }

    public void setAge(@Min(18) @Max(80) int age) {
        this.age = age;
    }

    public String getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(String exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public int getExploringVolcanoId() {
        return exploringVolcanoId;
    }

    public void setExploringVolcanoId(int exploringVolcanoId) {
        this.exploringVolcanoId = exploringVolcanoId;
    }
}
