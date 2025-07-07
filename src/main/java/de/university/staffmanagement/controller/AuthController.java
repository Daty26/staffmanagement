package de.university.staffmanagement.controller;


import de.university.staffmanagement.dto.request.AuthRequestDTO;
import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.AuthResponseDTO;
//import io.swagger.v3.oas.annotations.Operation;
import de.university.staffmanagement.service.AuthService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication-related endpoints.
 *
 * <p>Provides login and token refresh functionalities for the staff management system.
 * Returns a JWT token upon successful authentication.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(
        name = "Controller for authentication/registration"
)
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates the user with provided credentials and returns a JWT token.
     *
     * @param authRequestDTO the login request containing username and password
     * @return an authentication response with access and refresh tokens
     */
    @PostMapping("/login")
    @Operation(
            summary = "Authentication"
    )
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.authenticate(authRequestDTO);
    }

    /**
     * Refreshes an expired JWT token using a valid refresh token.
     *
     * @param refreshTokenRequestDTO the request containing the refresh token
     * @return a new set of JWT and refresh tokens
     */
    @Operation(
            summary = "JWT token refreshing"
    )
    @PostMapping("/refreshToken")
    public AuthResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return authService.refreshToken(refreshTokenRequestDTO);
    }

}

