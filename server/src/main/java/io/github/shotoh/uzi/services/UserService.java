package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.uzi.exceptions.ResourceNotFoundException;
import io.github.shotoh.uzi.mappers.UserMapper;
import io.github.shotoh.uzi.models.users.User;
import io.github.shotoh.uzi.models.users.UserDTO;
import io.github.shotoh.uzi.repositories.UserRepository;
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

    public long toId(User user) {
        return user.getId();
    }

    public User toUser(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "User not found with this id"));
    }

    public List<UserDTO> retrieveUsers() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (repository.existsById(userDTO.getId())) {
            throw new ResourceAlreadyExistsException("id", "User already exists with this id");
        }
        if (repository.existsByUsername(userDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("username", "User already exists with this username");
        }
        if (repository.existsByEmail(userDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("email", "User already exists with this email");
        }
        User user = repository.save(mapper.toEntity(userDTO));
        return mapper.toDTO(user);
    }

    public UserDTO retrieveUser(long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "User not found with this id"));
        return mapper.toDTO(user);
    }

    public UserDTO updateUser(long id, UserDTO userDTO) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "User not found with this id"));
        mapper.updateEntity(userDTO, user);
        repository.save(user);
        return mapper.toDTO(user);
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
