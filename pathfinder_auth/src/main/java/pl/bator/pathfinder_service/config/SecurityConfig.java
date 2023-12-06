package pl.bator.pathfinder_service.config;

import io.vavr.control.Option;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.bator.pathfinder_service.entity.Role;
import pl.bator.pathfinder_service.infrastructure.OidcAuthService;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${pathfinder-auth.security.loginUri}")
    private String loginUri;
    @Value("${pathfinder-auth.security.logoutUri}")
    private String logoutUri;
    @Value("${pathfinder-auth.security.redirectUri}")
    private String redirectUri;
    @Value("${pathfinder-auth.security.allowedRedirectUris}")
    private String[] allowedRedirectUris;
    private final OidcAuthService oidcAuthService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .oauth2Login(login -> login
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcAuthService))
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

    @Configuration
    @Data
    @ConfigurationProperties(prefix = "pathfinder-auth.security")
    public static class SecurityProperties {
        private Map<String, Set<String>> privileges = new LinkedHashMap<>();

        public Set<SimpleGrantedAuthority> getPrivileges(Role role) {
            return Option
                    .of(privileges.get(role.toString()))
                    .map(Collection::stream)
                    .map(stream -> stream.map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
                    .getOrElse(Collections::emptySet);
        }
    }

}
