package io.github.shotoh.snippet.models.users;

import io.github.shotoh.snippet.models.SnippetModel;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements SnippetModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false, updatable = false)
	private String username;

	@Column(unique = true, nullable = false, updatable = false)
	private String email;

	@Column(nullable = false)
	private String encryptedPassword;

	private String displayName;

	private String profilePicture;

	private String biography;

	@Column(nullable = false)
	private String role = "USER";

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();

	@Override
	public long userId() {
		return id;
	}
}
