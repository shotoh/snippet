package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.posts.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByUserId(long userId);
}
