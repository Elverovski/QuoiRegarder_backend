package org.example.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private int score;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    private LocalDateTime createdAt = LocalDateTime.now();
}
