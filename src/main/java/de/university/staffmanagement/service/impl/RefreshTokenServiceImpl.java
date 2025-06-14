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

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

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



    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
