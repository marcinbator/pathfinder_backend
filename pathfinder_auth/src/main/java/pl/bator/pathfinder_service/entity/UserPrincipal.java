package pl.bator.pathfinder_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
    private final String password = null;
    private final boolean isActive = true;
    private final Set<SimpleGrantedAuthority> authorities;
    private final transient Map<String, Object> attributes;
    public static UserPrincipal map(User user){
        return new UserPrincipal(
                user.getId(), user.getGoogleId(), user.getUsername(), user.getEmail(), user.getPhoto(),
                Set.of(new SimpleGrantedAuthority(user.getRole().toString())), Map.of(
                        "id", user.getId(),
                        "sub", user.getGoogleId(),
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "login", user.getUsername(),
                        "avatar_url", user.getPhoto())
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
