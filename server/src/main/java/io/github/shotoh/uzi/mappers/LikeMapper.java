package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.likes.Like;
import io.github.shotoh.uzi.models.likes.LikeDTO;
import io.github.shotoh.uzi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface LikeMapper {
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    @Mapping(target = "timestamp", ignore = true)
    Like toEntity(LikeDTO postDTO);

    @Mapping(source = "user", target = "userId")
    @Mapping(source = "post", target = "postId")
    LikeDTO toDTO (Like post);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(LikeDTO postDTO, @MappingTarget Like post);
}
