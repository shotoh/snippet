package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.uzi.exceptions.ResourceNotFoundException;
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
        if (repository.existsById(user.getId())) {
            throw new ResourceAlreadyExistsException("id", "User already exists with that id");
        }
        if (repository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("username", "User already exists with that username");
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("email", "User already exists with that email");
        }
        return repository.save(user);
    }

    public User updateUser(User user) {
        User userToUpdate = repository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("id", "User not found with that id"));
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setEncryptedPass(user.getEncryptedPass());
        userToUpdate.setDisplayName(user.getDisplayName());
        userToUpdate.setProfilePicture(user.getProfilePicture());
        userToUpdate.setBiography(user.getBiography());
        repository.save(userToUpdate);
        return userToUpdate; // TODO USE DTO
    }

    public void deleteUser(User user) {
        //
    }
}
