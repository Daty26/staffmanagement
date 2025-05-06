//package de.university.staffmanagement.service.impl;
//
//import de.university.staffmanagement.dto.request.AuthRequestDTO;
//import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
//import de.university.staffmanagement.dto.response.AuthResponseDTO;
//import de.university.staffmanagement.entity.RefreshToken;
//import de.university.staffmanagement.service.RefreshTokenService;
//import org.springframework.security.authentication.AuthenticationManager;
//import de.university.staffmanagement.repository.UserRepository;
//import de.university.staffmanagement.service.AuthService;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//public class AuthServiceImpl implements AuthService {
//    private final AuthenticationManager authenticationManager;
//    private final RefreshTokenService refreshTokenService;
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final ConfirmationTokenRepository confirmationTokenRepository;
//    private final MailSenderService emailService;
//
//    @Override
//    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
//        if (authentication.isAuthenticated()) {
//            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
//            return AuthResponseDTO.builder()
//                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
//                    .refreshToken(refreshToken.getToken())
//                    .build();
//        } else {
//            throw new UsernameNotFoundException("invalid user request..!!");
//        }
//    }
//
//    @Override
//    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
//        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(customer -> {
//                    String accessToken = jwtService.GenerateToken(customer.getUsername());
//                    return AuthResponseDTO.builder()
//                            .accessToken(accessToken)
//                            .refreshToken(refreshTokenRequestDTO.getToken()).build();
//                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
//    }
//}
