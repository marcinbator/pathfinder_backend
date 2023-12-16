package pl.bator.pathfinder_service.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pl.bator.pathfinder_service.types.entity.Location;
import pl.bator.pathfinder_service.types.entity.User;

import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {
    private Long id;
    private String googleId;
    private String username;
    private final Role role = Role.ROLE_USER;
    private String email;
    private String photo;
    private Location primaryLocation;
    private User.AccountType accountType;
    private final String password = null;
    private final boolean isActive = true;
    private final Set<SimpleGrantedAuthority> authorities;
    private final transient Map<String, Object> attributes;

    public static UserPrincipal map(User user, Set<SimpleGrantedAuthority> authorities, Map<String, Object> attributes) {
        return new UserPrincipal(
                user.getId(), user.getGoogleId(), user.getUsername(), user.getEmail(), user.getPhoto(), user.getPrimaryLocation(), user.getAccountType(),
                authorities, attributes
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public String getName() {
        return username;
    }
}
