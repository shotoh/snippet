package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.comments.Comment;
import io.github.shotoh.snippet.models.comments.CommentCreateDTO;
import io.github.shotoh.snippet.models.comments.CommentDTO;
import io.github.shotoh.snippet.services.PostService;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface CommentMapper {
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	Comment toEntity(CommentCreateDTO commentCreateDTO);

	CommentDTO toDTO(Comment comment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "post", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(CommentDTO commentDTO, @MappingTarget Comment comment);
}
