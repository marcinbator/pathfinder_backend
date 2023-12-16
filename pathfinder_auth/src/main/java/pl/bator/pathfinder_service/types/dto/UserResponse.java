package pl.bator.pathfinder_service.types.dto;

import lombok.Data;
import pl.bator.pathfinder_service.types.Role;
import pl.bator.pathfinder_service.types.entity.Location;
import pl.bator.pathfinder_service.types.entity.User;

@Data
public class UserResponse {
    private Long id;
    private String googleId;
    private String username;
    private String email;
    private String photo;
    private Role role;
    private Location primaryLocation;
    private User.AccountType accountType;
}
