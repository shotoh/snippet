package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.users.User;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "password", target = "encryptedPassword")
    @Mapping(target = "timestamp", ignore = true)
    User toEntity(UserCreateDTO userCreateDTO);

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(UserDTO userDTO, @MappingTarget User user);
}
