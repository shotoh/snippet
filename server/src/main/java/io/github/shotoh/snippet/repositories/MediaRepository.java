package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.medias.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
}
