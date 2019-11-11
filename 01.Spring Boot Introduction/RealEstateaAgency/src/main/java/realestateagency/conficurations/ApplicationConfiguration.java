package realestateagency.conficurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realestateagency.util.HtmlReader;

import javax.validation.Validation;
import javax.validation.Validator;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public HtmlReader htmlReader() {
        return new HtmlReader();
    }
}
