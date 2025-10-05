package org.example.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int episodeNumber;
    private int seasonNumber;
    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serieId;

}
