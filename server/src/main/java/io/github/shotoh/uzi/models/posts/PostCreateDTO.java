package io.github.shotoh.uzi.models.posts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PostCreateDTO {
    private long id;

    @Positive
    private long userId;

    @NotNull
    @Size(min = 1, max = 63)
    private String title;

    @NotNull
    @Size(min = 1, max = 1023)
    private String content;
}
