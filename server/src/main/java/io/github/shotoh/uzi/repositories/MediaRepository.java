package io.github.shotoh.uzi.repositories;

import io.github.shotoh.uzi.models.medias.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
}
