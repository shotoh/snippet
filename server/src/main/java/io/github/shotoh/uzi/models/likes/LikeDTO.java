package io.github.shotoh.uzi.models.likes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LikeDTO {
    private long id;

    @NotNull(groups = LikeCreate.class)
    @Positive(groups = LikeCreate.class)
    private long userId;

    @NotNull(groups = LikeCreate.class)
    @Positive(groups = LikeCreate.class)
    private long postId;

    public interface LikeCreate {
        //
    }
}
