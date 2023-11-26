package pl.bator.pathfinder_service.infrastructure;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bator.pathfinder_service.config.SecurityConfig;
import pl.bator.pathfinder_service.entity.Role;
import pl.bator.pathfinder_service.entity.User;
import pl.bator.pathfinder_service.entity.UserPrincipal;
import pl.bator.pathfinder_service.entity.repository.UserRepository;

import java.util.Map;

@Service
@AllArgsConstructor
public class OidcAuthService extends OidcUserService {
    private final UserRepository userRepository;
    private final SecurityConfig.SecurityProperties securityProperties;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        var oidcUser = super.loadUser(userRequest);
        var user = createUser(oidcUser.getAttributes());
        userRepository.save(user);
        return new DefaultOidcUser(
                UserPrincipal.map(user, securityProperties.getPrivileges(user.getRole()), oidcUser.getAttributes()).getAuthorities(),
                oidcUser.getIdToken(),
                "email");
    }

    private User createUser(Map<String, Object> attributes) {
        return Option.ofOptional(userRepository
                        .getByEmailOrUsername((String) attributes.get("email"), (String) attributes.get("name")))
                .getOrElse(() -> {
                    User newUser = new User();
                    newUser.setUsername((String) attributes.get("name"));
                    newUser.setEmail((String) attributes.get("email"));
                    newUser.setPhoto((String) attributes.get("picture"));
                    newUser.setGoogleId((String) attributes.get("sub"));
                    newUser.setRole(Role.ROLE_USER);
                    return newUser;
                });
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return Option.ofOptional(userRepository.getByEmailOrUsername(username, username))
                .getOrElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
