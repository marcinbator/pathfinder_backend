package pl.bator.pathfinder_service.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    @Value("${pathfinder-auth.security.loginUri}")
    private String loginUri;
    @Value("${pathfinder-auth.security.logoutUri}")
    private String logoutUri;
    @Value("${pathfinder-auth.security.redirectUri}")
    private String redirectUri;
    @Value("${pathfinder-auth.security.allowedRedirectUris}")
    private String[] allowedRedirectUris;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .oauth2Login(login -> login
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri(loginUri))
                        .defaultSuccessUrl(redirectUri))
                .logout(logout -> logout
                        .logoutUrl(logoutUri)
                        .logoutSuccessUrl(redirectUri)
                        .invalidateHttpSession(false)
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(handlingConfigurer -> handlingConfigurer
                        .authenticationEntryPoint((request, response, authException) -> response
                                .sendError(HttpServletResponse.SC_UNAUTHORIZED)));
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(allowedRedirectUris)
                        .allowCredentials(true);
            }
        };
    }
}
