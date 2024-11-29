package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistsRootImportDto {
    @XmlElement(name = "volcanologist")
    private List<VolcanologistImportDto> volcanologistImportDtos;

    public VolcanologistsRootImportDto() {
        this.volcanologistImportDtos = new ArrayList<>();
    }

    public List<VolcanologistImportDto> getVolcanologistImportDtos() {
        return volcanologistImportDtos;
    }

    public void setVolcanologistImportDtos(List<VolcanologistImportDto> volcanologistImportDtos) {
        this.volcanologistImportDtos = volcanologistImportDtos;
    }
}
