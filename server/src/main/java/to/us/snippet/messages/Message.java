package to.us.snippet.messages;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Table(name = "messages")
public class Message implements SnippetModel {
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

	@Column(nullable = false)
	private String content;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();

	@Override
	public long userId() {
		return from.userId();
	}
}
