package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.AuthRequestDTO;
import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
import de.university.staffmanagement.dto.response.AuthResponseDTO;
import de.university.staffmanagement.entity.RefreshToken;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Role;
import de.university.staffmanagement.mapper.UserMapper;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.RefreshTokenService;
import de.university.staffmanagement.service.impl.AuthServiceImpl;
import de.university.staffmanagement.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_shouldReturnAuthResponse_whenValidCredentials() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("alice");
        request.setPassword("password");

        User user = new User();
        user.setUsername("alice");
        user.setRole(Role.EMPLOYEE);

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        RefreshToken refreshToken = RefreshToken.builder().token("refresh-token").user(user).build();
        when(refreshTokenService.createRefreshToken("alice")).thenReturn(refreshToken);

        when(jwtService.GenerateToken("alice", Role.EMPLOYEE)).thenReturn("access-token");

        AuthResponseDTO response = authService.authenticate(request);

        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }

    @Test
    void authenticate_shouldThrow_whenUserNotFound() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("bob");
        request.setPassword("secret");

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);

        when(userRepository.findByUsername("bob")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.authenticate(request));
    }

    @Test
    void authenticate_shouldThrow_whenNotAuthenticated() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("alice");
        request.setPassword("wrong");

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(false);

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(new User()));

        assertThrows(UsernameNotFoundException.class, () -> authService.authenticate(request));
    }

    @Test
    void refreshToken_shouldReturnAccessToken_whenTokenIsValid() {
        RefreshTokenRequestDTO request = new RefreshTokenRequestDTO();
        request.setToken("valid-token");

        User user = new User();
        user.setUsername("alice");
        user.setRole(Role.MANAGER);

        RefreshToken refreshToken = RefreshToken.builder()
                .token("valid-token")
                .user(user)
                .build();

        when(refreshTokenService.findByToken("valid-token")).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(jwtService.GenerateToken("alice", Role.MANAGER)).thenReturn("access-token");

        AuthResponseDTO response = authService.refreshToken(request);

        assertEquals("access-token", response.getAccessToken());
        assertEquals("valid-token", response.getRefreshToken());
    }

    @Test
    void refreshToken_shouldThrow_whenTokenInvalid() {
        RefreshTokenRequestDTO request = new RefreshTokenRequestDTO();
        request.setToken("invalid");

        when(refreshTokenService.findByToken("invalid")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.refreshToken(request));
    }
}

