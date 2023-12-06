package pl.bator.pathfinder_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String googleId;
    private String username;
    @Email
    private String email;
    private String photo;
    @Enumerated(EnumType.STRING)
    private Role role;
}
