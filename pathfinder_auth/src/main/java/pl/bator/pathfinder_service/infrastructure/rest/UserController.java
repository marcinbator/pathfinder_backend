package pl.bator.pathfinder_service.infrastructure.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.bator.pathfinder_service.infrastructure.UserService;
import pl.bator.pathfinder_service.types.UserUpdateRequest;
import pl.bator.pathfinder_service.types.dto.UserResponse;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public void updateInfo(@RequestBody UserUpdateRequest request) {
        userService.updateUserInfo(request);
    }
}
