package de.university.staffmanagement.controller;


import de.university.staffmanagement.dto.request.AuthRequestDTO;
import de.university.staffmanagement.dto.request.RefreshTokenRequestDTO;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.AuthResponseDTO;
//import io.swagger.v3.oas.annotations.Operation;
import de.university.staffmanagement.service.AuthService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
//@Tag(
//        name = "Controller for authentication/registration"
//)
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
//    @Operation(
//            summary = "Authentication"
//    )
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.authenticate(authRequestDTO);
    }

//    @Operation(
//            summary = "JWT token refreshing"
//    )
    @PostMapping("/refreshToken")
    public AuthResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return authService.refreshToken(refreshTokenRequestDTO);
    }

}

