package io.github.shotoh.snippet.models.medias;

import io.github.shotoh.snippet.models.posts.PostDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MediaDTO {
    private long id;

    private PostDTO post;

    @Size(min = 1, max = 1023)
    private String source;
}
