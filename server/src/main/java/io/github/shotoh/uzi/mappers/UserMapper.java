package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.User;
import io.github.shotoh.uzi.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO (User user);

    void updateEntity(UserDTO userDTO, @MappingTarget User user);
}
