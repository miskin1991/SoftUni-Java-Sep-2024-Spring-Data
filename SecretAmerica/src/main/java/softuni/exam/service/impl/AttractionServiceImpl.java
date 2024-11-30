package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AttractionImportDto;
import softuni.exam.models.entity.Attraction;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.AttractionService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    public static final String FILE_PATH = "src/main/resources/files/json/attractions.json";

    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository,
                                 CountryRepository countryRepository,
                                 Gson gson,
                                 ModelMapper modelMapper,
                                 ValidationUtil validationUtil) {

        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return attractionRepository.count() > 0;
    }

    @Override
    public String readAttractionsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAttractions() throws IOException {
        StringBuilder sb = new StringBuilder();

        AttractionImportDto[] attractionImportDtos =
                gson.fromJson(readAttractionsFileContent(), AttractionImportDto[].class);

        for (AttractionImportDto attractionImportDto : attractionImportDtos) {
            if (validationUtil.isValid(attractionImportDto)
                    && attractionRepository.findAttractionByName(attractionImportDto.getName()).isEmpty()) {
                Attraction attraction = modelMapper.map(attractionImportDto, Attraction.class);
                attraction.setCountry(countryRepository.findCountryById(attractionImportDto.getCountry()).get());
                attractionRepository.saveAndFlush(attraction);

                sb.append(String.format("Successfully imported attraction %s", attractionImportDto.getName()))
                        .append(System.lineSeparator());
            } else {
                sb.append("Invalid attraction").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportAttractions() {
        StringBuilder sb = new StringBuilder();

        List<Attraction> attractions = attractionRepository.
                findByTypeContainsIgnoreCaseOrTypeContainsIgnoreCaseAndElevationGreaterThanEqualOrderByNameAsc(
                "historical site", "archaeological site", 300.00);

        attractions.forEach(attraction -> {
            sb.append(String.format("Attraction with ID%d:\n***%s - %s at an altitude of %dm. somewhere in %s.",
                                    attraction.getId(), attraction.getName(), attraction.getDescription(),
                                    attraction.getElevation(), attraction.getCountry().getName()))
                .append(System.lineSeparator());
        });

        return sb.toString();
    }
}
