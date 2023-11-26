package pl.bator.pathfinder_service.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bator.pathfinder_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> getByEmailOrUsername(String email, String name);
}
