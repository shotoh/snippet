package to.us.snippet.friends;

import to.us.snippet.SnippetModel;
import to.us.snippet.users.User;
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

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "from_id", nullable = false, updatable = false)
	private User from;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "to_id", nullable = false, updatable = false)
	private User to;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FriendStatus status = FriendStatus.PENDING;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();

	@Override
	public long userId() {
		return from.userId();
	}
}
