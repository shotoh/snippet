package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.images.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	Image findFirstByUserId(long userId);

	List<Image> findAllByPostId(long postId);
}
