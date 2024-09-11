package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.uzi.models.User;
import io.github.shotoh.uzi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("username", "User with that username already exists");
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("email", "User with that email already exists");
        }
        return repository.save(user);
    }
}
