package pl.bator.pathfinder_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

    private final Long id;
    private final String username;
    private final String password = null;
    private final String email;
    private final AccountStatus status;
    private final Set<SimpleGrantedAuthority> authorities;
    private final transient Map<String, Object> attributes;

    private UserPrincipal(Long id, String username, String email, AccountStatus status, Set<SimpleGrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.status = status;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static UserPrincipal create(UserPrincipal userPrincipal, Set<SimpleGrantedAuthority> authorities) {
        return new UserPrincipal(
                userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.email,
                new AccountStatus(
                        userPrincipal.status.isExpired, userPrincipal.status.isLocked,
                        userPrincipal.status.isCredentialsExpired, userPrincipal.status.isEnabled),
                authorities, userPrincipal.attributes
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return !status.isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !status.isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return status.isEnabled;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Getter
    @AllArgsConstructor
    private static class AccountStatus implements Serializable {
        private final Boolean isExpired;
        private final Boolean isLocked;
        private final Boolean isCredentialsExpired;
        private final Boolean isEnabled;
    }
}