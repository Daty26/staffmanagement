package de.university.staffmanagement.service;

import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_shouldReturnUser_whenFound() {
        // Arrange
        User user = new User();
        user.setUsername("john");
        user.setPassword("secret");
        user.setRole(null); // Optional if Role is used in UserDetails

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("john");

        // Assert
        assertEquals("john", result.getUsername());
        verify(userRepository).findByUsername("john");
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenNotFound() {
        // Arrange
        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknown");
        });
    }
}

