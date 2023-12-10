package pl.bator.pathfinder_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

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
    @OneToOne
    private Location primaryLocation;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}

enum AccountType {
    STUDENT, MANAGER, ADMIN
}