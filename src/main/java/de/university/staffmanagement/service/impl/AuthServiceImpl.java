package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.AuthRequestDTO;
import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
import de.university.staffmanagement.dto.response.AuthResponseDTO;
import de.university.staffmanagement.entity.RefreshToken;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.mapper.UserMapper;
import de.university.staffmanagement.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Service implementation for handling authentication and JWT token management.
 *
 * <p>This class provides logic for user login and refreshing JWT access tokens using refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user using the provided credentials and returns a pair of access and refresh tokens.
     *
     * @param authRequestDTO contains the username and password
     * @return an {@link AuthResponseDTO} containing the generated JWT access token and refresh token
     * @throws UsernameNotFoundException if authentication fails or user is not found
     */
    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        User user = userRepository.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return AuthResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername(), user.getRole()))
                    .refreshToken(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    /**
     * Generates a new access token using a valid refresh token.
     *
     * @param refreshTokenRequestDTO contains the refresh token
     * @return an {@link AuthResponseDTO} containing the new JWT access token and the same refresh token
     * @throws RuntimeException if the refresh token is not found or is expired
     */
    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(customer -> {
                    String accessToken = jwtService.GenerateToken(customer.getUsername(), customer.getRole());
                    return AuthResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
