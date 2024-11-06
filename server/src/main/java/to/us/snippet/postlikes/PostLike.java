package to.us.snippet.postlikes;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import to.us.snippet.SnippetModel;
import to.us.snippet.posts.Post;
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
@Table(name = "post_likes")
public class PostLike implements SnippetModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id", nullable = false, updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	private int value;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();

	@Override
	public long userId() {
		return user.userId();
	}
}
