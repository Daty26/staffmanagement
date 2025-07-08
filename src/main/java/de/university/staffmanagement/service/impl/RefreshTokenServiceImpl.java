package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.entity.RefreshToken;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.repository.RefreshTokenRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the {@link de.university.staffmanagement.service.RefreshTokenService} interface.
 *
 * <p>This service manages the creation, retrieval, and validation of refresh tokens for user authentication.
 *
 * <p>Main functionalities include:
 * <ul>
 *     <li>Generating a new refresh token (or reusing an existing one)</li>
 *     <li>Looking up a refresh token by its string value</li>
 *     <li>Verifying whether a token is expired and handling cleanup</li>
 * </ul>
 *
 * <p>Uses {@link RefreshTokenRepository} for persistence and {@link UserRepository} to look up users by username.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    /**
     * Creates and returns a refresh token for the specified username.
     * If a token already exists for the user, it is reused.
     *
     * @param username the username for which to generate the token
     * @return a refresh token entity (new or existing)
     * @throws RuntimeException if the user with the given username does not exist
     */
    public RefreshToken createRefreshToken(String username) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUserUsername(username);

        if (refreshTokenOptional.isPresent()) {
            return refreshTokenOptional.get();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(36000000)) // 10 hours
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    /**
     * Finds a refresh token by its token string value.
     *
     * @param token the token string to search for
     * @return an optional containing the token if found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifies whether the refresh token is still valid (i.e., not expired).
     * If the token is expired, it is deleted and an exception is thrown.
     *
     * @param token the refresh token to check
     * @return the same token if it is still valid
     * @throws RuntimeException if the token is expired
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
