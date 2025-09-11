package org.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Serie {
    @Id
    private Long id;
    private String titre;
    private String genre;
    private int nbEpisodes;
    private float note;
}
