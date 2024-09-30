package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.friends.Friend;
import io.github.shotoh.snippet.models.friends.FriendDTO;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface FriendMapper {
	FriendDTO toDTO(Friend friend);
}
