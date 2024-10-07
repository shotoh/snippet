package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.postlikes.PostLike;
import io.github.shotoh.snippet.models.postlikes.PostLikeCreateDTO;
import io.github.shotoh.snippet.models.postlikes.PostLikeDTO;
import io.github.shotoh.snippet.services.PostService;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface PostLikeMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	PostLike toEntity(PostLikeCreateDTO postLikeCreateDTO);

	PostLikeDTO toDTO(PostLike postLike);
}
