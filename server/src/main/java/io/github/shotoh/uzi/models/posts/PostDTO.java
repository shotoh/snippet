package io.github.shotoh.uzi.models.posts;

import io.github.shotoh.uzi.models.users.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PostDTO {
    private long id;

    private User user;

    @NotNull(groups = PostCreate.class)
    @Size(min = 1, max = 63)
    private String title;

    @NotNull(groups = PostCreate.class)
    @Size(min = 1, max = 1023)
    private String content;

    public interface PostCreate {
        //
    }
}
