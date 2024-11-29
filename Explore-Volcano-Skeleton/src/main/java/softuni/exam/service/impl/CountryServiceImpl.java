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
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                              Gson gson,
                              ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString( Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        CountryImportDto[] countryImportDtos = gson.fromJson(readCountriesFromFile(), CountryImportDto[].class);
        StringBuilder sb = new StringBuilder();
        for (CountryImportDto country : countryImportDtos) {
            if (validationUtil.isValid(country)
                    && countryRepository.getCountryByName(country.getName()).isEmpty()) {
                countryRepository.saveAndFlush(modelMapper.map(country, Country.class));
                sb.append(String.format("Successfully imported country %s - %s", country.getName(), country.getCapital()))
                        .append(System.lineSeparator());
            } else {
                sb.append("Invalid country").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
