package io.github.shotoh.snippet.models.medias;

import io.github.shotoh.snippet.models.posts.Post;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "medias")
public class Media {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false, updatable = false)
	private Post post;

	@Column(nullable = false)
	private String source;

	@Column(nullable = false, updatable = false)
	private long timestamp = Instant.now().toEpochMilli();
}
