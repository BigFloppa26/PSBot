package ustin.psbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Base64;

@SpringBootApplication()
public class PsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsBotApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {return new ObjectMapper();}

    @Bean
    public ModelMapper mapper() {return new ModelMapper();}
}
