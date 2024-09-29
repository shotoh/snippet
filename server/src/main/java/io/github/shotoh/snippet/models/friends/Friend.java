package io.github.shotoh.snippet.models.friends;

import io.github.shotoh.snippet.models.SnippetModel;
import io.github.shotoh.snippet.models.users.User;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "friends")
public class Friend implements SnippetModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "from_id", nullable = false, updatable = false)
	private User from;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "to_id", nullable = false, updatable = false)
	private User to;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FriendStatus status;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();

	@Override
	public long userId() {
		return from.userId();
	}
}
