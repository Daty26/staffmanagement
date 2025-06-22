package de.university.staffmanagement.controller;


import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.service.UserService;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "Controller for getting, updating users"
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Creating user"
    )
//    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.create(userRequestDTO)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Updating user"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUser(@RequestParam String username,
                                                      @RequestParam String email,
                                                      @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.update(user, username, email)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Getting user by id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.get(id)), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Getting all users"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> getAllUsers() {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.getAll()), HttpStatus.OK);
    }

}
