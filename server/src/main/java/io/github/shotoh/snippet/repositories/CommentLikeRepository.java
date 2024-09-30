package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.commentlikes.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	boolean existsByUserIdAndCommentId(long userId, long commentId);
}
