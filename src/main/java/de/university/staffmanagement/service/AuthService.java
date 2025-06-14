package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.AuthRequestDTO;
import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
import de.university.staffmanagement.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

}
