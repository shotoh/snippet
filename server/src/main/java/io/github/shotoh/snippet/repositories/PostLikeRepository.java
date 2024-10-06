package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.postlikes.PostLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	List<PostLike> findAllByPostId(long postId);

	PostLike findPostLikeByUserIdAndPostId(long userId, long postId);

	boolean existsByUserIdAndPostId(long userId, long postId);
}
