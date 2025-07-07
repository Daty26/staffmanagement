package de.university.staffmanagement.service;

import de.university.staffmanagement.entity.RefreshToken;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.repository.RefreshTokenRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.RefreshTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRefreshToken_shouldReturnExistingToken_ifAlreadyExists() {
        String username = "testuser";
        RefreshToken existingToken = new RefreshToken();
        existingToken.setToken("existing-token");

        when(refreshTokenRepository.findByUserUsername(username)).thenReturn(Optional.of(existingToken));

        RefreshToken result = refreshTokenService.createRefreshToken(username);

        assertEquals("existing-token", result.getToken());
        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    void createRefreshToken_shouldCreateNewToken_ifNotExists() {
        String username = "newuser";

        when(refreshTokenRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        RefreshToken savedToken = RefreshToken.builder()
                .token("generated-token")
                .user(user)
                .expiryDate(Instant.now().plusMillis(36000000))
                .build();

        when(refreshTokenRepository.save(any())).thenReturn(savedToken);

        RefreshToken result = refreshTokenService.createRefreshToken(username);

        assertEquals("generated-token", result.getToken());
        verify(refreshTokenRepository).save(any());
    }

    @Test
    void createRefreshToken_shouldThrow_ifUserNotFound() {
        String username = "ghost";

        when(refreshTokenRepository.findByUserUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            refreshTokenService.createRefreshToken(username);
        });

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void findByToken_shouldReturnToken_ifExists() {
        String token = "abc123";
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);

        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        assertTrue(result.isPresent());
        assertEquals(token, result.get().getToken());
    }

    @Test
    void verifyExpiration_shouldReturnToken_ifNotExpired() {
        RefreshToken token = new RefreshToken();
        token.setToken("valid");
        token.setExpiryDate(Instant.now().plusSeconds(600));

        RefreshToken result = refreshTokenService.verifyExpiration(token);

        assertEquals("valid", result.getToken());
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    void verifyExpiration_shouldThrowAndDelete_ifExpired() {
        RefreshToken token = new RefreshToken();
        token.setToken("expired");
        token.setExpiryDate(Instant.now().minusSeconds(10));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            refreshTokenService.verifyExpiration(token);
        });

        assertTrue(ex.getMessage().contains("Refresh token is expired"));
        verify(refreshTokenRepository).delete(token);
    }
}

