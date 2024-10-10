package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.comments.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPostId(long postId);
}
