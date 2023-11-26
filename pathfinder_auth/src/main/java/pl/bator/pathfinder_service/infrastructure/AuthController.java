package pl.bator.pathfinder_service.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/get")
@Tag(name = "Auth", description = "Authentication controller")
public class AuthController {
    private final AuthService authService;
    @Value("${pathfinder-auth.security.loginUri}")
    private String loginUri;
    @Value("${pathfinder-auth.security.logoutUri}")
    private String logoutUri;

    @GetMapping("/me")
    @Operation(summary = "Get current user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> me() {
        var user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user.getAttributes().toString());
    }

    @GetMapping("/token")
    @Operation(summary = "Get session token for backend communication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getSessionToken() {
        return ResponseEntity.ok(authService.getToken());
    }

    @GetMapping("/login")
    @Operation(summary = "Get login uri (paste it in browser)")
    public ResponseEntity<String> getLoginUri() {
        return ResponseEntity.ok(loginUri);
    }

    @Operation(summary = "Get logout uri (paste it in browser)")
    @GetMapping("/logout")
    public ResponseEntity<String> getLogoutUri() {
        return ResponseEntity.ok(logoutUri);
    }

    @GetMapping
    @Operation(summary = "Synchronous connection to backend test")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> isInDb() {
        return ResponseEntity.ok(authService.connectBackend());
    }
}
