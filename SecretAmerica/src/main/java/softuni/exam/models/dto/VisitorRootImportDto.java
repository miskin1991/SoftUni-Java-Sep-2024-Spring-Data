package softuni.exam.models.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;


@JacksonXmlRootElement(localName = "visitors")
public class VisitorRootImportDto {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "visitor")
    List<VisitorImportDto> visitors;

    public List<VisitorImportDto> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<VisitorImportDto> visitors) {
        this.visitors = visitors;
    }
}
