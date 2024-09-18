package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.commentlikes.CommentLike;
import io.github.shotoh.snippet.models.commentlikes.CommentLikeCreateDTO;
import io.github.shotoh.snippet.models.commentlikes.CommentLikeDTO;
import io.github.shotoh.snippet.services.CommentService;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserService.class, CommentService.class })
public interface CommentLikeMapper {
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "commentId", target = "comment")
    @Mapping(target = "timestamp", ignore = true)
    CommentLike toEntity(CommentLikeCreateDTO commentLikeCreateDTO);

    CommentLikeDTO toDTO(CommentLike commentLike);
}
