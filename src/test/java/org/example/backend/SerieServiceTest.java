package org.example.backend;

import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.SerieRepository;
import org.example.backend.service.UserService;
import org.example.backend.service.SerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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









}
