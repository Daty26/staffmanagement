package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.AuthRequestDTO;
import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
import de.university.staffmanagement.dto.response.AuthResponseDTO;

/**
 * Service interface for handling authentication-related operations such as login and token refreshing.
 *
 * <p>This service is responsible for authenticating users using credentials and
 * issuing or refreshing JWT access tokens.
 */
public interface AuthService {
    /**
     * Authenticates a user using the provided username and password.
     *
     * @param authRequestDTO contains the login credentials (username and password)
     * @return an authentication response containing the access and refresh tokens
     */
    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO);

    /**
     * Issues a new access token based on the provided refresh token.
     *
     * @param refreshTokenRequestDTO contains the refresh token and associated username
     * @return a refreshed authentication response with a new access token
     */
    AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

}
