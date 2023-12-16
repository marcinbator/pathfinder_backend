package pl.bator.pathfinder_service.types;

import lombok.Data;
import pl.bator.pathfinder_service.types.entity.Location;
import pl.bator.pathfinder_service.types.entity.User;

@Data
public class UserUpdateRequest {
    private Location primaryLocation;
    private User.AccountType accountType;
}
