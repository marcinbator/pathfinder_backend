package pl.bator.pathfinder_service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@AllArgsConstructor
@RequestMapping("/api/get")
public class ServiceController {
    private WebClient webClient;

    @GetMapping("")
    public ResponseEntity<String> isInDb() {
        return ResponseEntity.ok(webClient.get()
                .uri("http://localhost:8080/api/record")
                .retrieve()
                .bodyToMono(String.class)
                .block());
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> me() {
        var user = (OidcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user.getEmail());
    }
}
