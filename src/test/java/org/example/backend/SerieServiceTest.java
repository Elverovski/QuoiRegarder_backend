package org.example.backend;

import org.example.backend.exception.ModelNotFoundException;
import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.SerieRepository;
import org.example.backend.service.UserService;
import org.example.backend.service.SerieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SerieServiceTest {

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SerieService seriesService;

    @InjectMocks
    private UserService userService;

    private Serie serie1;
    private Serie serie2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        serie1 = new Serie(); serie1.setId(1L); serie1.setTitre("Serie 1"); serie1.setGenre("Action"); serie1.setNbepisodes(10);

        serie2 = new Serie(); serie2.setId(2L); serie2.setTitre("Serie 2"); serie2.setGenre("Comedy"); serie2.setNbepisodes(8);
    }

    // Create
    @Test
    void testCreateSerie() {
        when(serieRepository.save(serie1)).thenReturn(serie1);
        Serie saved = seriesService.createSerie(serie1);

        assertEquals("Serie 1", saved.getTitre());
    }

    // Delete
    @Test
    void testDeleteSerie() {
        when(serieRepository.existsById(1L)).thenReturn(true);
        seriesService.deleteSerie(1L);
    }


    // Serch By Genre
    @Test
    void testSearchSerieByGenre() {
        when(serieRepository.findSerieByGenre("Action")).thenReturn(Arrays.asList(serie1));

        List<Serie> results = seriesService.searchSerie("Action");
        assertEquals("Serie 1", results.get(0).getTitre());
    }


    @Test
    void testAddToHistory() {
        User user = new User();
        user.setId(5L);
        user.setEmail("test@example.com");
        user.setHistory(new ArrayList<>());

        Serie serie = new Serie();
        serie.setId(2L);
        serie.setTitre("Testing");

        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(serieRepository.findSerieById(2L)).thenReturn(serie);
        when(userRepository.save(user)).thenReturn(user);

        userService.markSerieAsView("test@example.com", 2L);
        
        assertEquals(1, user.getHistory().size());
        assertEquals("Testing", user.getHistory().get(0).getTitre());
    }


    @Test
    void testFindAllSeries() {
        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2));

        List<Serie> allSeries = seriesService.findAllSeries();
        assertEquals(2, allSeries.size());
    }

    @Test
    void testFindSeriesByName() {
        when(serieRepository.findSerieByTitre("Serie 1")).thenReturn(serie1);

        Serie result = seriesService.findSeriesByName("Serie 1");
        assertEquals("Serie 1", result.getTitre());
    }

    @Test
    void testFindSeriesById() {
        when(serieRepository.findSerieById(1L)).thenReturn(serie1);

        Serie result = seriesService.findSeriesById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateSerieSuccess() {
        when(serieRepository.findById(1L)).thenReturn(Optional.of(serie1));
        when(serieRepository.save(any(Serie.class))).thenReturn(serie1);

        Serie updated = seriesService.updateSerie(serie1, 1L);
        assertEquals("Serie 1", updated.getTitre());
    }

    @Test
    void testUpdateSerieNotFound() {
        when(serieRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ModelNotFoundException.class, () -> {
            seriesService.updateSerie(serie1, 99L);
        });
    }

    @Test
    void testSearchSerieByNbEpisodes() {
        when(serieRepository.findSerieByNbepisodesGreaterThanEqual(9)).thenReturn(Arrays.asList(serie1));

        List<Serie> result = seriesService.searchSerie(9);
        assertEquals(1, result.size());
        assertEquals("Serie 1", result.get(0).getTitre());
    }

    @Test
    void testGetAllTendances() {
        when(serieRepository.findAllByOrderByNoteDesc()).thenReturn(Arrays.asList(serie1, serie2));

        List<Serie> tendances = seriesService.getAllTendances();
        assertEquals(2, tendances.size());
        assertEquals("Serie 1", tendances.get(0).getTitre());
    }
}
