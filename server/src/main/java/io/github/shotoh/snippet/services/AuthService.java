package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.models.auth.AuthDTO;
import io.github.shotoh.snippet.models.auth.TokenDTO;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.security.SnippetUserDetailsService;
import io.github.shotoh.snippet.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final SnippetUserDetailsService snippetUserDetailsService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserService userService, SnippetUserDetailsService snippetUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.snippetUserDetailsService = snippetUserDetailsService;
    }

    public void register(UserCreateDTO userCreateDTO) {
        userService.createUser(userCreateDTO);
    }

    public TokenDTO login(AuthDTO authDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        UserDetails userDetails = snippetUserDetailsService.loadUserByUsername(authDTO.getUsername());
        return new TokenDTO(JwtUtils.generateToken(userDetails));
    }
}
