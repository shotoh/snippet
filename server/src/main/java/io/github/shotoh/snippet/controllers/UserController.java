package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
//@Secured("ROLE_ADMIN")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<?> retrieveUsers(Authentication authentication) {
        return new Success<>(authentication.getAuthorities());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Success<UserDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        return new Success<>(service.createUser(userCreateDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<UserDTO> retrieveUser(@PathVariable("id") long id) {
        return new Success<>(service.retrieveUser(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<UserDTO> updateUser(@PathVariable("id") long id, @RequestBody @Valid UserDTO userDTO) {
        return new Success<>(service.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Success<Void> deleteUser(@PathVariable("id") long id) {
        service.deleteUser(id);
        return new Success<>();
    }
}
