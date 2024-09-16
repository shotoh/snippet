package io.github.shotoh.snippet.models.messages;

import io.github.shotoh.snippet.models.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", nullable = false, updatable = false)
    private User from;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", nullable = false, updatable = false)
    private User to;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private long timestamp = Instant.now().toEpochMilli();
}
