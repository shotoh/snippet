package to.us.snippet.posts;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import to.us.snippet.SnippetModel;
import to.us.snippet.images.PostImage;
import to.us.snippet.users.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
public class Post implements SnippetModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@Column(nullable = false)
	private String content;

	@OneToMany(targetEntity = PostImage.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "images")
	private List<PostImage> images;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();

	@Override
	public long userId() {
		return user.userId();
	}
}
