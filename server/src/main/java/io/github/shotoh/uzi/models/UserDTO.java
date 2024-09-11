package io.github.shotoh.uzi.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDTO {
    private static final int USERNAME_MIN_LEN = 3;
    private static final int USERNAME_MAX_LEN = 31;

    private long id;

    @NotNull(groups = UserCreate.class)
    @Size(min = USERNAME_MIN_LEN, max = USERNAME_MAX_LEN)
    private String username;

    @NotNull(groups = UserCreate.class)
    @Email
    private String email;

    @NotNull(groups = UserCreate.class)
    private String encryptedPass;

    @Size(min = USERNAME_MIN_LEN, max = USERNAME_MAX_LEN)
    private String displayName;

    private String profilePicture;

    private String biography;

    public interface UserCreate {
        //
    }
}
