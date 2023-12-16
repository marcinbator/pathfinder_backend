package pl.bator.pathfinder_service.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bator.pathfinder_service.infrastructure.OidcAuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication controller")
public class AuthController {
    private final OidcAuthService oidcAuthService;
    @Value("${pathfinder-auth.security.loginUri}")
    private String loginUri;
    @Value("${pathfinder-auth.security.logoutUri}")
    private String logoutUri;

    @GetMapping("/token")
    @Operation(summary = "Get session token for backend communication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getSessionToken() {
        return ResponseEntity.ok(oidcAuthService.getToken());
    }

    @GetMapping("/login")
    @Operation(summary = "Get login uri (paste it in browser)")
    public ResponseEntity<String> getLoginUri() {
        return ResponseEntity.ok("http://localhost:8081" + loginUri + "/google");
    }

    @Operation(summary = "Get logout uri (paste it in browser)")
    @GetMapping("/logout")
    public ResponseEntity<String> getLogoutUri() {
        return ResponseEntity.ok("http://localhost:8081" + logoutUri);
    }

}
