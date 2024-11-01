package to.us.snippet.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.auth.AuthService;
import to.us.snippet.exceptions.ResourceAlreadyExistsException;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.images.ImageService;

@Service
public class UserService {
	private final UserRepository repository;
	private final UserMapper mapper;

	private final AuthService authService;
	private final ImageService imageService;

	@Autowired
	public UserService(UserRepository repository, UserMapper mapper, AuthService authService, ImageService imageService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
		this.imageService = imageService;
	}

	public User getUser(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "User not found with this id"));
	}

	public List<UserDTO> retrieveUsers() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public UserDTO createUser(UserCreateDTO userCreateDTO) {
		if (repository.existsByUsername(userCreateDTO.getUsername())) {
			throw new ResourceAlreadyExistsException("username", "User already exists with this username");
		}
		if (repository.existsByEmail(userCreateDTO.getEmail())) {
			throw new ResourceAlreadyExistsException("email", "User already exists with this email");
		}
		String encryptedPassword = authService.encryptPassword(userCreateDTO.getPassword());
		userCreateDTO.setPassword(encryptedPassword);
		User user = repository.save(mapper.toEntity(userCreateDTO));
		return mapper.toDTO(user);
	}

	public UserDTO retrieveUser(long id) {
		User user = getUser(id);
		return mapper.toDTO(user);
	}

	public UserDTO updateUser(long id, UserDTO userDTO) {
		User user = getUser(id);
		authService.check(user);
		mapper.updateEntity(userDTO, user);
		repository.save(user);
		return mapper.toDTO(user);
	}

	public void updateUserProfilePicture(long id, MultipartFile file) {
		User user = getUser(id);
		authService.check(user);
		String oldPicture = user.getProfilePicture();
		imageService.deleteImage(oldPicture);
		String url = imageService.saveImage(file);
		user.setProfilePicture(url);
		repository.save(user);
	}

	public void deleteUser(long id) {
		User user = getUser(id);
		authService.check(user);
		imageService.deleteImage(user.getProfilePicture());
		repository.deleteById(user.getId());
	}
}
