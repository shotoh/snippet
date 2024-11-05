package to.us.snippet.friends;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import to.us.snippet.SnippetModel;
import to.us.snippet.users.User;

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
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User from;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "to_id", nullable = false, updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
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
