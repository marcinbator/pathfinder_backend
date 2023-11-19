package pl.bator.pathfinder_service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class AppConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
