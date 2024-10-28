package to.us.snippet.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.auth.AuthService;
import to.us.snippet.exceptions.ResourceAlreadyExistsException;
import to.us.snippet.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	private final UserRepository repository;
	private final UserMapper mapper;

	private final AuthService authService;

	@Autowired
	public UserService(UserRepository repository, UserMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public User getUser(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "User not found with this id"));
	}

	public List<UserDTO> retrieveUsers() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public UserDTO createUser(UserCreateDTO userCreateDTO) {
		if (repository.existsById(userCreateDTO.getId())) {
			throw new ResourceAlreadyExistsException("id", "User already exists with this id");
		}
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

	public void deleteUser(long id) {
		User user = getUser(id);
		authService.check(user);
		repository.deleteById(user.getId());
	}
}
