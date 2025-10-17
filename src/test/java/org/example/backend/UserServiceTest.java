package org.example.backend;

import org.example.backend.exception.ModelNotFoundException;
import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.repository.SerieRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SerieRepository serieRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private Serie serie1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");
        user1.setNom("Doe");
        user1.setHistory(new ArrayList<>());

        serie1 = new Serie();
        serie1.setId(1L);
        serie1.setTitre("Serie 1");
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1));
        List<User> users = userService.findAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void testFindUsersByName() {
        when(userRepository.findUserByNom("Doe")).thenReturn(user1);
        User user = userService.findUsersByName("Doe");
        assertEquals("Doe", user.getNom());
    }

    @Test
    void testFindUserById() {
        when(userRepository.findUserById(1L)).thenReturn(user1);
        User user = userService.findUserById(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testFindHistoryByEmail() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user1);
        List<Serie> history = userService.findHistoryByEmail("test@example.com");
        assertNotNull(history);
    }

    @Test
    void testFindHistoryByEmailUserNotFound() {
        when(userRepository.findUserByEmail("unknown@example.com")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            userService.findHistoryByEmail("unknown@example.com");
        });
    }

    @Test
    void testMarkSerieAsView() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user1);
        when(serieRepository.findSerieById(1L)).thenReturn(serie1);
        when(userRepository.save(user1)).thenReturn(user1);

        String message = userService.markSerieAsView("test@example.com", 1L);
        assertEquals("serie a été marqué comme vue", message);
        assertEquals(1, user1.getHistory().size());
    }

    @Test
    void testMarkSerieAsViewAlreadyInHistory() {
        user1.getHistory().add(serie1);
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user1);
        when(serieRepository.findSerieById(1L)).thenReturn(serie1);

        assertThrows(RuntimeException.class, () -> {
            userService.markSerieAsView("test@example.com", 1L);
        });
    }

    @Test
    void testUpdateUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        User updated = new User();
        updated.setNom("NewName");
        updated.setPrenom("John");
        updated.setEmail("john@example.com");
        updated.setGenre("M");

        User result = userService.updateUser(updated, 1L);
        assertEquals("NewName", result.getNom());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        User updated = new User();
        assertThrows(ModelNotFoundException.class, () -> {
            userService.updateUser(updated, 99L);
        });
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user1)).thenReturn(user1);
        User result = userService.createUser(user1);
        assertEquals("test@example.com", result.getEmail());
    }
}
