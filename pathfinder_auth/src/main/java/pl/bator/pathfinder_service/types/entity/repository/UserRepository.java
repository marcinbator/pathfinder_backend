package pl.bator.pathfinder_service.types.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bator.pathfinder_service.types.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmailOrUsername(String email, String name);
}
