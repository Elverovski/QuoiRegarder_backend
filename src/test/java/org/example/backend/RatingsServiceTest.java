package org.example.backend;

import org.example.backend.models.Episode;
import org.example.backend.models.Ratings;
import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.repository.EpisodeRepository;
import org.example.backend.repository.RatingsRepository;
import org.example.backend.repository.SerieRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.RatingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RatingsServiceTest {

    @Mock
    private RatingsRepository ratingsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SerieRepository serieRepository;
    @Mock
    private EpisodeRepository episodeRepository;

    @InjectMocks
    private RatingsService ratingsService;

    private User user;
    private Serie serie;
    private Episode episode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        serie = new Serie();
        serie.setId(1L);

        episode = new Episode();
        episode.setId(1L);
    }

    @Test
    void testRateEpisodeSuccess() {
        when(episodeRepository.findEpisodeById(1L)).thenReturn(episode);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndEpisode(user, episode)).thenReturn(null);

        String res = ratingsService.rateEpisode(1L, "test@example.com", 50);
        assertEquals("Episode rate avec succès", res);
        verify(ratingsRepository, times(1)).save(any(Ratings.class));
    }

    @Test
    void testRateEpisodeEpisodeNotFound() {
        when(episodeRepository.findEpisodeById(1L)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> ratingsService.rateEpisode(1L, "test@example.com", 50));
    }

    @Test
    void testRateEpisodeUserNotFound() {
        when(episodeRepository.findEpisodeById(1L)).thenReturn(episode);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> ratingsService.rateEpisode(1L, "test@example.com", 50));
    }

    @Test
    void testRateEpisodeAlreadyRated() {
        when(episodeRepository.findEpisodeById(1L)).thenReturn(episode);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndEpisode(user, episode)).thenReturn(new Ratings());

        assertThrows(RuntimeException.class, () -> ratingsService.rateEpisode(1L, "test@example.com", 50));
    }

    @Test
    void testRateSerieSuccess() {
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndSerie(user, serie)).thenReturn(null);
        when(ratingsRepository.getAverageSerieRating(1L)).thenReturn(80.0);

        String res = ratingsService.rateSerie(1L, "test@example.com", 80);
        assertEquals("Serie rate avec succès", res);
        verify(serieRepository, times(1)).save(serie);
    }

    @Test
    void testRateSerieSerieNotFound() {
        when(serieRepository.findSerieById(1L)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> ratingsService.rateSerie(1L, "test@example.com", 50));
    }

    @Test
    void testRateSerieUserNotFound() {
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> ratingsService.rateSerie(1L, "test@example.com", 50));
    }

    @Test
    void testRateSerieAlreadyRated() {
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndSerie(user, serie)).thenReturn(new Ratings());

        assertThrows(RuntimeException.class, () -> ratingsService.rateSerie(1L, "test@example.com", 50));
    }

    @Test
    void testRateSerieInvalidScore() {
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndSerie(user, serie)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> ratingsService.rateSerie(1L, "test@example.com", 120));
        assertThrows(RuntimeException.class, () -> ratingsService.rateSerie(1L, "test@example.com", -5));
    }

    @Test
    void testUpdateRateSerieSuccess() {
        Ratings rating = new Ratings();
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndSerie(user, serie)).thenReturn(rating);
        when(ratingsRepository.getAverageSerieRating(1L)).thenReturn(90.0);

        String res = ratingsService.updateRateSerie(1L, "test@example.com", 90);
        assertEquals("Serie update rate avec succès", res);
        verify(ratingsRepository, times(1)).save(rating);
    }

    @Test
    void testUpdateRateSerieRateNotFound() {
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndSerie(user, serie)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> ratingsService.updateRateSerie(1L, "test@example.com", 50));
    }

    @Test
    void testUpdateRateSerieInvalidScore() {
        Ratings rating = new Ratings();
        when(serieRepository.findSerieById(1L)).thenReturn(serie);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        when(ratingsRepository.findRatingsByUserIdAndSerie(user, serie)).thenReturn(rating);

        assertThrows(RuntimeException.class, () -> ratingsService.updateRateSerie(1L, "test@example.com", 101));
        assertThrows(RuntimeException.class, () -> ratingsService.updateRateSerie(1L, "test@example.com", -1));
    }

    @Test
    void testGetAverageSerieRating() {
        when(ratingsRepository.getAverageSerieRating(1L)).thenReturn(75.0);
        assertEquals(75.0, ratingsService.getAverageSerieRating(1L));
    }

    @Test
    void testGetAverageEpisodeRating() {
        when(ratingsRepository.getAverageEpisodeRating(1L)).thenReturn(80.0);
        assertEquals(80.0, ratingsService.getAverageEpisodeRating(1L));
    }
}
