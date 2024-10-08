package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.UserMapper;
import io.github.shotoh.snippet.models.users.User;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import io.github.shotoh.snippet.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
        User user = repository.save(mapper.toEntity(userCreateDTO));
        return mapper.toDTO(user);
    }

    public UserDTO retrieveUser(long id) {
        User user = getUser(id);
        return mapper.toDTO(user);
    }

    public UserDTO updateUser(long id, UserDTO userDTO) {
        User user = getUser(id);
        mapper.updateEntity(userDTO, user);
        repository.save(user);
        return mapper.toDTO(user);
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
