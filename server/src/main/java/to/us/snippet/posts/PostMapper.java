package to.us.snippet.posts;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import to.us.snippet.auth.AuthService;
import to.us.snippet.images.PostImage;
import to.us.snippet.postlikes.PostLike;
import to.us.snippet.postlikes.PostLikeRepository;
import to.us.snippet.users.UserService;

@Mapper(componentModel = "spring", uses = {UserService.class})
public abstract class PostMapper {
	@Autowired
	AuthService authService;
	@Autowired
	PostLikeRepository repository;
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	public abstract Post toEntity(PostCreateDTO postCreateDTO);

	@Mapping(target = "liked", ignore = true)
	@Mapping(target = "totalLikes", ignore = true)
	@Mapping(target = "totalDislikes", ignore = true)
	@Mapping(target = "images", qualifiedByName = "getPostImages")
	public abstract PostDTO toDTO(Post post);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	public abstract void updateEntity(PostDTO postDTO, @MappingTarget Post post);

	@Named("getPostImages")
	public List<String> getPostImages(List<PostImage> images) {
		if (images == null) return List.of();
		return images.stream().map(PostImage::getContent).toList();
	}

	@AfterMapping
	protected void setLikes(Post post, @MappingTarget PostDTO postDTO) {
		PostLike postLike = repository.getPostLikeByUserIdAndPostId(authService.userId(), post.getId());
		if (postLike != null) {
			postDTO.setLiked(postLike.getValue());
		} else {
			postDTO.setLiked(0);
		}
		postDTO.setTotalLikes(repository.countAllByPostIdAndValue(post.getId(), 1));
		postDTO.setTotalDislikes(repository.countAllByPostIdAndValue(post.getId(), -1));
	}
}
