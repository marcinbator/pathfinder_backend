package pl.bator.pathfinder_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Pathfinder_auth microservice API documentation", description = "Author: Marcin Bator"))
public class AppConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
