package io.github.shotoh.snippet.models.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AuthDTO {
    private String username;
    private String password;
}
