package softuni.exam.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VisitorRootImportDto;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.VisitorService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VisitorServiceImpl implements VisitorService {

    private static final String FILE_PATH = "src/main/resources/files/xml/visitors.xml";

    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;
    private final PersonalDataRepository personalDataRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final VisitorRepository visitorRepository;
    private final XmlMapper xmlMapper;


    @Autowired
    public VisitorServiceImpl(AttractionRepository attractionRepository,
                              CountryRepository countryRepository,
                              PersonalDataRepository personalDataRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil,
                              VisitorRepository visitorRepository,
                              XmlMapper xmlMapper) {
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.personalDataRepository = personalDataRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.visitorRepository = visitorRepository;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public boolean areImported() {
        return visitorRepository.count() > 0;
    }

    @Override
    public String readVisitorsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVisitors() throws IOException {
        StringBuilder sb = new StringBuilder();

        VisitorRootImportDto visitorRootImportDto =
                xmlMapper.readValue(readVisitorsFileContent(), VisitorRootImportDto.class);

        visitorRootImportDto.getVisitors()
                .forEach(visitorImportDto -> {
                    if (validationUtil.isValid(visitorImportDto)
                            && visitorRepository.findVisitorByFirstNameAndLastName(
                                    visitorImportDto.getFirstName(), visitorImportDto.getLastName()).isEmpty()
                            && visitorRepository.findVisitorByPersonalDataId(visitorImportDto.getPersonalData())
                                .isEmpty()) {

                        Visitor visitor = modelMapper.map(visitorImportDto, Visitor.class);
                        visitor.setAttraction(attractionRepository
                                .findById((long) visitorImportDto.getAttraction()).get());
                        visitor.setCountry(countryRepository.findById((long) visitorImportDto.getCountry()).get());
                        visitor.setPersonalData(personalDataRepository
                                .findById((long) visitorImportDto.getPersonalData()).get());

                        visitorRepository.saveAndFlush(visitor);

                        sb.append(String.format("Successfully imported visitor %s %s",
                                visitor.getFirstName(), visitor.getLastName()))
                            .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid visitor").append(System.lineSeparator());
                    }
                });

        return sb.toString();
    }
}
