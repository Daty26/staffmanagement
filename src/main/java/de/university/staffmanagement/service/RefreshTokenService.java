package de.university.staffmanagement.service;

import de.university.staffmanagement.entity.RefreshToken;

import java.util.Optional;

/**
 * Service interface for managing refresh tokens used in the authentication process.
 *
 * <p>This service provides functionality to:
 * <ul>
 *     <li>Create a refresh token for a user</li>
 *     <li>Look up a refresh token by its string value</li>
 *     <li>Verify whether a refresh token is still valid or expired</li>
 * </ul>
 */
public interface RefreshTokenService {
    /**
     * Creates a new refresh token for the specified username.
     * If a token already exists for the user, it is returned.
     *
     * @param username the username of the user
     * @return the created or existing refresh token entity
     */
    RefreshToken createRefreshToken(String username);

    /**
     * Finds a refresh token by its token string value.
     *
     * @param token the string value of the refresh token
     * @return an Optional containing the refresh token if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Verifies whether the given refresh token is still valid.
     * If the token is expired, it is deleted and an exception is thrown.
     *
     * @param token the refresh token to verify
     * @return the same token if it is still valid
     * @throws RuntimeException if the token has expired
     */
    RefreshToken verifyExpiration(RefreshToken token);
}
