package to.us.snippet.images;

import to.us.snippet.images.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	Image findFirstByUserIdAndPostNull(long userId);

	List<Image> findAllByPostId(long postId);
}
