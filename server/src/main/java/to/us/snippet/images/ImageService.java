package to.us.snippet.images;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.exceptions.InvalidRequestException;
import to.us.snippet.exceptions.ResourceNotFoundException;

@Service
public class ImageService {
	private final PostImageRepository repository;
	private final String imagePath;
	//private final ImageMapper mapper;

	//private final AuthService authService;
	//private final PostService postService;

	@Autowired
	public ImageService(PostImageRepository repository, @Value("${IMAGE_PATH:}") String imagePath) {
		this.repository = repository;
		//this.mapper = mapper;
		//this.authService = authService;
		//this.postService = postService;
		this.imagePath = imagePath;
	}

	public PostImage getImage(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Image not found with this id"));
	}

	public List<ImageDTO> retrieveImagesByPost(long postId) {
		return List.of();
		//return repository.findAllByPostId(postId).stream().map(mapper::toDTO).toList();
	}

	public ImageDTO createImage(ImageCreateDTO imageCreateDTO) {
		long postId = imageCreateDTO.getPostId();
		if (postId != 0) {
			//Post post = postService.getPost(postId);
			//authService.check(post);
		}
		//imageCreateDTO.setUserId(authService.userId());
		return new ImageDTO();
		//Image image = repository.save(mapper.toEntity(imageCreateDTO));
		//return mapper.toDTO(image);
	}

	public ImageDTO retrieveImage(long id) {
		PostImage image = getImage(id);
		return new ImageDTO();
		//return mapper.toDTO(image);
	}

	public void deleteImage(long id) {
		PostImage image = getImage(id);
		//authService.check(image);
		repository.deleteById(image.getId());
	}

	public String saveImage(MultipartFile file) {
		if (file.isEmpty()) {
			throw new InvalidRequestException("file", "File is empty");
		}
		String type = file.getContentType();
		String originalName = file.getOriginalFilename();
		if (type == null || !type.startsWith("image")) {
			throw new InvalidRequestException("file", "File is not an image");
		}
		if (originalName == null) {
			throw new InvalidRequestException("file", "Invalid file name");
		}
		Path uploadPath = Paths.get(imagePath);
		if (!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		String fileName = imagePath + UUID.randomUUID() + "." + StringUtils.getFilenameExtension(StringUtils.cleanPath(originalName));
		Path path = Paths.get(fileName);
		try {
			Files.write(path, file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileName.substring(1);
	}
}
