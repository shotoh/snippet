package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.likes.Like;
import io.github.shotoh.uzi.models.likes.LikeCreateDTO;
import io.github.shotoh.uzi.models.likes.LikeDTO;
import io.github.shotoh.uzi.services.PostService;
import io.github.shotoh.uzi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserService.class, PostService.class })
public interface LikeMapper {
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    @Mapping(target = "timestamp", ignore = true)
    Like toEntity(LikeCreateDTO likeCreateDTO);

    LikeDTO toDTO(Like like);
}
