package pl.bator.pathfinder_service.infrastructure;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.bator.pathfinder_service.config.JwtUtil;

@Service
@AllArgsConstructor
public class AuthService {
    private final WebClient webClient;
    private final JwtUtil jwtUtil;

    //deprecation
    String connectBackend() {
        return webClient.get()
                .uri("http://localhost:8080/api/record")
                .header("Authorization", "Bearer " + getToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    String getToken() {
        return Option.of(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof OidcUser)
                .map(o -> (OidcUser) o)
                .map(user -> jwtUtil.generateToken(new JwtUtil.Input(
                        user.getEmail() //todo authorities
                )))
                .getOrNull();
    }
}
