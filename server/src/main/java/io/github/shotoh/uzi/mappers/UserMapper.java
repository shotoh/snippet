package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.users.User;
import io.github.shotoh.uzi.models.users.UserCreateDTO;
import io.github.shotoh.uzi.models.users.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "timestamp", ignore = true)
    User toEntity(UserDTO userDTO);

    @Mapping(target = "timestamp", ignore = true)
    User toEntity(UserCreateDTO userCreateDTO);

    UserDTO toDTO (User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "encryptedPass", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(UserDTO userDTO, @MappingTarget User user);
}
