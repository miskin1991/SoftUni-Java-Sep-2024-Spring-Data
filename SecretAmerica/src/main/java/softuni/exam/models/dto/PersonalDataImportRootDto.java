package softuni.exam.models.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "personal_datas")
public class PersonalDataImportRootDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "personal_data")
    private List<PersonalDataImportDto> personalDataImportDtos;

    public List<PersonalDataImportDto> getPersonalDataDtos() {
        return personalDataImportDtos;
    }

    public void setPersonalDataImportDtos(List<PersonalDataImportDto> personalDataImportDtos) {
        this.personalDataImportDtos = personalDataImportDtos;
    }
}
