package to.us.snippet.posts;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.auth.AuthService;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.friends.Friend;
import to.us.snippet.friends.FriendService;
import to.us.snippet.friends.FriendStatus;
import to.us.snippet.images.ImageService;
import to.us.snippet.images.PostImage;
import to.us.snippet.images.PostImageRepository;
import to.us.snippet.postlikes.PostLike;
import to.us.snippet.postlikes.PostLikeRepository;
import to.us.snippet.users.User;

@Service
public class PostService {
	private final PostRepository repository;
	private final PostImageRepository imageRepository;
	private final PostLikeRepository likeRepository;
	private final PostMapper mapper;

	private final AuthService authService;
	private final ImageService imageService;
	private final FriendService friendService;

	@Autowired
	public PostService(PostRepository repository, PostImageRepository imageRepository, PostLikeRepository likeRepository,
	                   PostMapper mapper, AuthService authService, ImageService imageService, FriendService friendService) {
		this.repository = repository;
		this.imageRepository = imageRepository;
		this.likeRepository = likeRepository;
		this.mapper = mapper;
		this.authService = authService;
		this.imageService = imageService;
		this.friendService = friendService;
	}

	public Post getPost(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Post not found with this id"));
	}

	public List<String> getPostImages(List<PostImage> images) {
		if (images == null) return List.of();
		return images.stream().map(PostImage::getContent).toList();
	}

	public List<PostDTO> retrievePosts() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public List<PostDTO> retrieveTrendingPosts() {
		return retrievePosts().stream()
				.limit(10)
				.sorted(Comparator.comparingInt(PostDTO::getTotalLikes).reversed())
				.toList();
	}

	public List<PostDTO> retrieveDiscoverPosts() {
		return retrievePosts().stream()
				.limit(20)
				.sorted(Comparator.comparingLong(PostDTO::getTimestamp).reversed())
				.toList();
	}

	public List<PostDTO> retrieveMainPagePosts() {
		List<PostDTO> list = new ArrayList<>(retrievePosts().stream().limit(20).toList());
		list.sort(Comparator.comparingLong(PostDTO::getTimestamp).reversed());
		List<PostDTO> newList = new ArrayList<>();
		Iterator<PostDTO> iterator = list.iterator();
		while (iterator.hasNext()) {
			PostDTO post = iterator.next();
			Friend friend = friendService.getFriendByFromAndTo(authService.userId(), post.getUser().getId());
			if (friend != null && friend.getStatus() == FriendStatus.FRIEND) {
				newList.add(post);
				iterator.remove();
			}
		}
		newList.addAll(list);
		return newList;
	}

	public List<PostDTO> retrievePostsByUser(long userId) {
		return repository.findAllByUserId(userId).stream().map(mapper::toDTO).toList();
	}

	public PostDTO createPost(PostCreateDTO postCreateDTO) {
		postCreateDTO.setUserId(authService.userId());
		Post post = repository.save(mapper.toEntity(postCreateDTO));
		return mapper.toDTO(post);
	}

	public PostDTO retrievePost(long id) {
		Post post = getPost(id);
		return mapper.toDTO(post);
	}

	public PostDTO updatePost(long id, PostDTO postDTO) {
		Post post = getPost(id);
		authService.check(post);
		mapper.updateEntity(postDTO, post);
		repository.save(post);
		return mapper.toDTO(post);
	}

	public void addPostPicture(long id, MultipartFile file) {
		Post post = getPost(id);
		authService.check(post);
		String url = imageService.saveImage(file);
		PostImage postImage = new PostImage();
		postImage.setPost(post);
		postImage.setContent(url);
		imageRepository.save(postImage);
		repository.save(post);
	}

	@Transactional
	public void likePost(long id) {
		User user = authService.getUser();
		Post post = getPost(id);
		unlikePost(id);
		PostLike postLike = new PostLike();
		postLike.setUser(user);
		postLike.setPost(post);
		postLike.setValue(1);
		likeRepository.save(postLike);
	}

	@Transactional
	public void dislikePost(long id) {
		User user = authService.getUser();
		Post post = getPost(id);
		unlikePost(id);
		PostLike postLike = new PostLike();
		postLike.setUser(user);
		postLike.setPost(post);
		postLike.setValue(-1);
		likeRepository.save(postLike);
	}

	@Transactional
	public void unlikePost(long id) {
		User user = authService.getUser();
		Post post = getPost(id);
		likeRepository.deletePostLikeByUserIdAndPostId(user.getId(), post.getId());
	}

	public void deletePost(long id) {
		Post post = getPost(id);
		authService.check(post);
		for (PostImage postImage : post.getImages()) {
			imageService.deleteImage(postImage.getContent());
		}
		repository.deleteById(post.getId());
	}
}
