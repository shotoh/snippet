package io.github.shotoh.snippet.security;

import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.models.users.User;
import io.github.shotoh.snippet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("username", "User not found with this username");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(user.getEncryptedPassword())
                .passwordEncoder(encoder::encode)
                .roles("USER")
                .build();
    }
}
