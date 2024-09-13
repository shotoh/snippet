package io.github.shotoh.uzi.repositories;

import io.github.shotoh.uzi.models.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
