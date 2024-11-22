package to.us.snippet.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "password", target = "encryptedPassword")
	@Mapping(target = "displayName", ignore = true)
	@Mapping(target = "biography", ignore = true)
	@Mapping(target = "profilePicture", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	User toEntity(UserCreateDTO userCreateDTO);

	UserDTO toDTO(User user);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "profilePicture", ignore = true)
	@Mapping(target = "encryptedPassword", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(UserDTO userDTO, @MappingTarget User user);
}
