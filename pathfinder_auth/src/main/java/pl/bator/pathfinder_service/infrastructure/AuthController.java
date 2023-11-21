package pl.bator.pathfinder_service.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/get")
public class AuthController {
    private final AuthService authService;
    @GetMapping
    public ResponseEntity<String> isInDb() {
        return ResponseEntity.ok(authService.connectBackend());
    }
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> me() {
        var user = (OidcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user.getEmail());
    }
    @GetMapping("/token")
    public ResponseEntity<String> getSessionToken() {
        return ResponseEntity.ok(authService.getToken());
    }
}
