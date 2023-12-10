package pl.bator.pathfinder_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {
    @Value("${pathfinder-auth.jwt.expiration-mins:15}")
    private int expirationMins;
    @Value("${pathfinder-auth.jwt.secret-key}")
    private String secretKey;

    public String generateToken(Input input) {
        final var authorities = input.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        final var claims = Map.of(
                "email", input.email,
                "authorities", authorities
        );
        return createToken(input.email, claims);
    }

    private String createToken(String subject, Map<String, ?> claims) {
        final Date createdDate = new Date(System.currentTimeMillis());
        final Date expirationDate = new Date(createdDate.getTime() + 1000L * 60 * expirationMins);
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Output deserializeToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token).getBody();
        return new Output(
                claims.get("email", String.class),
                claims.get("authorities", List.class)
        );
    }

    public boolean isTokenValid(String token) {
        var nowDate = new Date(System.currentTimeMillis());
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(nowDate);
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    @Data
    @AllArgsConstructor
    public static class Input {
        private String email;
        private Set<SimpleGrantedAuthority> authorities;
    }

    @Data
    @AllArgsConstructor
    public static class Output {
        private String email;
        private List<String> authorities;
    }
}
