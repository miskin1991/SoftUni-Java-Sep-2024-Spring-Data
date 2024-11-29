package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoImportDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    public static final String FILE_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository,
                              CountryRepository countryRepository,
                              Gson gson,
                              ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        VolcanoImportDto[] volcanoImportDtos = gson.fromJson(readVolcanoesFileContent(), VolcanoImportDto[].class);

        StringBuilder sb = new StringBuilder();

        for (VolcanoImportDto volcanoImportDto : volcanoImportDtos) {
            if (validationUtil.isValid(volcanoImportDto)
                    && volcanoRepository.findVolcanoByName(volcanoImportDto.getName()).isEmpty()) {
                Volcano volcano = modelMapper.map(volcanoImportDto, Volcano.class);
                volcano.setCountry(countryRepository.findById((long) volcanoImportDto.getCountry()).get());
                volcanoRepository.saveAndFlush(volcano);
                sb.append(
                        String.format("Successfully imported volcano %s of type %s",
                                volcanoImportDto.getName(), volcanoImportDto.getVolcanoType().toString()))
                        .append(System.lineSeparator());
            } else {
                sb.append("Invalid volcano").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();
        volcanoRepository
                .findAllByElevationAfterAndIsActiveAndLastEruptionIsNotNullOrderByElevationDesc(3000, true)
                .forEach(v -> {
                    sb.append(String.format("Volcano: %s\n" +
                            "   *Located in: %s\n" +
                            "   **Elevation: %d\n" +
                            "   ***Last eruption on: %s",
                            v.getName(), v.getCountry().getName(), v.getElevation(), v.getLastEruption()))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }
}