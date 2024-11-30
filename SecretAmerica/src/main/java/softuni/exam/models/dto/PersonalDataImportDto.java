package softuni.exam.models.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public class PersonalDataImportDto {

    @JacksonXmlProperty(localName = "age")
    @Min(value = 1)
    @Max(value = 100)
    private int age;

    @JacksonXmlProperty(localName = "birth_date")
    private String birthDate;

    @JacksonXmlProperty(localName = "card_number")
    @Length(min = 9, max = 9)
    private String cardNumber;

    @JacksonXmlProperty(localName = "gender")
    private char gender;

    public PersonalDataImportDto() {}

    @Min(value = 1)
    @Max(value = 100)
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 1) @Max(value = 100) int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public @Length(min = 9, max = 9) String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(@Length(min = 9, max = 9) String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
