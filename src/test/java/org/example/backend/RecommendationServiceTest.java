package org.example.backend;

import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.repository.SerieRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SerieRepository serieRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    private User user;
    private Serie serie1;
    private Serie serie2;
    private Serie serie3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        serie1 = new Serie(); serie1.setId(1L); serie1.setTitre("Serie 1"); serie1.setGenre("Action");
        serie2 = new Serie(); serie2.setId(2L); serie2.setTitre("Serie 2"); serie2.setGenre("Action");
        serie3 = new Serie(); serie3.setId(3L); serie3.setTitre("Serie 3"); serie3.setGenre("Comedy");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setHistory(new ArrayList<>(Arrays.asList(serie1, serie3)));
    }


    @Test
    void testGetRecommendationUserNotFound() {
        when(userRepository.findUserByEmail("unknown@example.com")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            recommendationService.getRecommendation("unknown@example.com");
        });
    }

    @Test
    void testGetRecommendationEmptyHistory() {
        User emptyUser = new User();
        emptyUser.setEmail("empty@example.com");
        emptyUser.setHistory(new ArrayList<>());

        when(userRepository.findUserByEmail("empty@example.com")).thenReturn(emptyUser);

        List<Serie> recommendations = recommendationService.getRecommendation("empty@example.com");
        assertTrue(recommendations.isEmpty());
    }
}
