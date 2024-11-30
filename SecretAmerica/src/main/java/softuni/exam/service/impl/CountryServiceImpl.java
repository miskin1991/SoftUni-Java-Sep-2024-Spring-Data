package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class CountryServiceImpl implements CountryService {

    public static final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                              Gson gson,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountryImportDto[] countryImportDtos = gson.fromJson(readCountryFileContent(), CountryImportDto[].class);

        for (CountryImportDto countryImportDto : countryImportDtos) {
            if (validationUtil.isValid(countryImportDto)
                    && countryRepository.findCountryByName(countryImportDto.getName()).isEmpty()) {
                countryRepository.saveAndFlush(modelMapper.map(countryImportDto, Country.class));

                sb.append(String.format("Successfully imported country %s", countryImportDto.getName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(String.format("Invalid country")).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
