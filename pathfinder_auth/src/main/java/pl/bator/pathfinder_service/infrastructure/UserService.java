package pl.bator.pathfinder_service.infrastructure;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bator.pathfinder_service.types.UserUpdateRequest;
import pl.bator.pathfinder_service.types.dto.UserResponse;
import pl.bator.pathfinder_service.types.entity.repository.UserRepository;
import pl.bator.pathfinder_service.types.mapper.UserMapper;

@Service
@AllArgsConstructor
public class UserService {
    private final OidcAuthService oidcAuthService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getCurrentUser() {
        return Option.of(oidcAuthService.getCurrentUser())
                .map(userMapper::map)
                .getOrElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public void updateUserInfo(UserUpdateRequest request) {
        var user = oidcAuthService.getCurrentUser();
        user.setPrimaryLocation(request.getPrimaryLocation());
        user.setAccountType(request.getAccountType());
        Option.of(userRepository.save(user))
                .getOrElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
