package io.github.shotoh.uzi.controllers;

import io.github.shotoh.uzi.models.User;
import io.github.shotoh.uzi.responses.Success;
import io.github.shotoh.uzi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Success<List<User>>> getAllUsers() {
        return ResponseEntity.ok(new Success<>(service.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<Success<User>> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(new Success<>(service.createUser(user)));
    }

    @PatchMapping
    public ResponseEntity<Success<User>> updateUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(new Success<>(service.updateUser(user)));
    }
}
