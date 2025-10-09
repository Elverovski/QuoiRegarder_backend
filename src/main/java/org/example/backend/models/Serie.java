package org.example.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String genre;
    private int nbepisodes;
    private Double note;
}
