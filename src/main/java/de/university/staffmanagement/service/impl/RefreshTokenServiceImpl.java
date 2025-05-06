//package de.university.staffmanagement.service.impl;
//
//import de.university.staffmanagement.entity.RefreshToken;
//import de.university.staffmanagement.repository.RefreshTokenRepository;
//import de.university.staffmanagement.repository.UserRepository;
//import de.university.staffmanagement.service.RefreshTokenService;
//
//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//public class RefreshTokenServiceImpl implements RefreshTokenService {
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final UserRepository userRepository;
//
//    public RefreshToken createRefreshToken(String username) {
//        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUserUsername(username);
//
//        if (refreshTokenOptional.isPresent()) {
//            return refreshTokenOptional.get();
//        }
//
//        RefreshToken refreshToken = RefreshToken.builder()
//                .user(userRepository.findByUsername(username))
//                .token(UUID.randomUUID().toString())
//                .expiryDate(Instant.now().plusMillis(36000000)) // 10 hours
//                .build();
//
//        return refreshTokenRepository.save(refreshToken);
//    }
//
//
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
//        }
//        return token;
//    }
//}
