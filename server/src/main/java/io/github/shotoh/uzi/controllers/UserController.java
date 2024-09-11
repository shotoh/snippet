package io.github.shotoh.uzi.controllers;

import io.github.shotoh.uzi.models.UserDTO;
import io.github.shotoh.uzi.responses.Success;
import io.github.shotoh.uzi.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<List<UserDTO>>  getAllUsers() {
        return new Success<>(service.getAllUsers());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Success<UserDTO> createUser(@RequestBody @Validated(UserDTO.UserCreate.class) UserDTO userDTO) {
        return new Success<>(service.createUser(userDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<UserDTO> retrieveUser(@PathVariable("id") long id) {
        return new Success<>(service.retrieveUser(id));
    }

    @PatchMapping("/{id}")
    public Success<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return new Success<>(service.updateUser(userDTO));
    }

    @DeleteMapping("/{id}")
    public Success<Void> deleteUser(@PathVariable("id") long id) {
        service.deleteUser(id);
        return new Success<>();
    }
}
