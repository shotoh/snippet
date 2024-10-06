package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.friends.Friend;
import io.github.shotoh.snippet.models.friends.FriendCreateDTO;
import io.github.shotoh.snippet.models.friends.FriendDTO;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface FriendMapper {
	@Mapping(source = "fromId", target = "from")
	@Mapping(source = "toId", target = "to")
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	Friend toEntity(FriendCreateDTO friendCreateDTO);

	FriendDTO toDTO(Friend friend);
}
