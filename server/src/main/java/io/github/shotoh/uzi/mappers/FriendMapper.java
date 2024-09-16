package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.friends.Friend;
import io.github.shotoh.uzi.models.friends.FriendDTO;
import io.github.shotoh.uzi.services.UserService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface FriendMapper {
    FriendDTO toDTO(Friend friend);
}
