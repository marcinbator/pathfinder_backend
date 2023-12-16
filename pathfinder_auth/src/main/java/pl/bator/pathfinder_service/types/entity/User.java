package pl.bator.pathfinder_service.types.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.Type;
import pl.bator.pathfinder_service.types.Role;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(unique = true)
    private String googleId;
    private String username;
    @Email
    private String email;
    private String photo;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Location primaryLocation;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public enum AccountType {
        STUDENT, MANAGER, ADMIN
    }
}
