package org.example.backend;

import org.example.backend.models.Serie;
import org.example.backend.models.Person;
import org.example.backend.repository.PersonRepository;
import org.example.backend.repository.SerieRepository;
import org.example.backend.service.PersonService;
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
    private PersonRepository personRepository;

    @InjectMocks
    private SerieService seriesService;

    @InjectMocks
    private PersonService personService;

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


    // Add to history
    @Test
    void testaddToHistory(){
        Person person = new Person();
        person.setId(5L);
        List<Serie> history = new ArrayList<>();
        person.setHistory(history);

        Serie serie = new Serie();
        serie.setId(2L);
        serie.setTitre("Testing");

        when(personRepository.findPersonById(5L)).thenReturn(person);
        when(serieRepository.findSerieById(2L)).thenReturn(serie);
        when(personRepository.save(person)).thenReturn(person);

        Person newOne = personService.markSerieAsView(5L, 2L);

        assertEquals(1, newOne.getHistory().size());
        assertEquals("Testing", newOne.getHistory().get(0).getTitre());
    }





}
