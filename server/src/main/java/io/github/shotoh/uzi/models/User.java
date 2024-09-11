package io.github.shotoh.uzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class User {
    private static final int USERNAME_MIN_LEN = 3;
    private static final int USERNAME_MAX_LEN = 31;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NotBlank
    @Size(min = USERNAME_MIN_LEN, max = USERNAME_MAX_LEN)
    private String username;

    @Column(unique = true, nullable = false, updatable = false)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String encryptedPass;

    @Size(min = USERNAME_MIN_LEN, max = USERNAME_MAX_LEN)
    private String displayName;

    private String profilePicture;

    private String biography;

    @Column(nullable = false)
    @NotBlank
    @PositiveOrZero
    private long timestamp = Instant.now().toEpochMilli();
}
