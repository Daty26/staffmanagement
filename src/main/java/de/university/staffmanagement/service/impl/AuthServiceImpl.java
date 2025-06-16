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


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
