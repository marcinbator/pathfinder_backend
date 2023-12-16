package pl.bator.pathfinder_service.types.mapper;

import org.mapstruct.Mapper;
import pl.bator.pathfinder_service.types.dto.UserResponse;
import pl.bator.pathfinder_service.types.entity.User;

@Mapper
public interface UserMapper {
    UserResponse map(User user);
}
