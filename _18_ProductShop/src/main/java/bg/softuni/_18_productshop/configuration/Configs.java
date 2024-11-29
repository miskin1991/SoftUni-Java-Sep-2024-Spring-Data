package bg.softuni._18_productshop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configs {
    @Bean
    ObjectMapper createJsonFactoryBuilder() {
         return new ObjectMapper();
    }
    @Bean
    ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
