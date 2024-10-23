package to.us.snippet.friends;

import to.us.snippet.users.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface FriendMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "fromId", target = "from")
	@Mapping(source = "toId", target = "to")
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	Friend toEntity(FriendCreateDTO friendCreateDTO);

	FriendDTO toDTO(Friend friend);
}
