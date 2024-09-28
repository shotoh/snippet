package io.github.shotoh.snippet.models.commentlikes;

import io.github.shotoh.snippet.models.comments.Comment;
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
@Table(name = "comment_likes")
public class CommentLike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id", nullable = false, updatable = false)
	private Comment comment;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();
}
