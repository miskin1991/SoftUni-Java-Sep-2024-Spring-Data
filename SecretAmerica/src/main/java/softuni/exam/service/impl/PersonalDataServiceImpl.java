package softuni.exam.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonalDataImportRootDto;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private static final String FILE_PATH = "src/main/resources/files/xml/personal_data.xml";

    private final PersonalDataRepository personalDataRepository;
    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository,
                                   XmlMapper xmlMapper,
                                   ObjectMapper objectMapper,
                                   ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.personalDataRepository = personalDataRepository;
        this.xmlMapper = xmlMapper;
        this.objectMapper = objectMapper;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPersonalData() throws IOException {
        StringBuilder sb = new StringBuilder();

        PersonalDataImportRootDto personalDataImportRootDto =
                xmlMapper.readValue(readPersonalDataFileContent(), PersonalDataImportRootDto.class);

        personalDataImportRootDto.getPersonalDataDtos()
                .forEach(personalDataImportDto -> {
                    LocalDate birthDay = LocalDate
                            .parse(personalDataImportDto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (validationUtil.isValid(personalDataImportDto)
                            && birthDay.isBefore(LocalDate.now())
                            && personalDataRepository.findByCardNumber(personalDataImportDto.getCardNumber()).isEmpty()) {
                        personalDataRepository.saveAndFlush(modelMapper.map(personalDataImportDto, PersonalData.class));

                        sb.append(
                                String.format("Successfully imported personal data for visitor with card number %s",
                                personalDataImportDto.getCardNumber()))
                            .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid personal data").append(System.lineSeparator());
                    }
                });

        return sb.toString();
    }
}
