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

/**
 * REST controller for managing user accounts.
 *
 * <p>Provides endpoints for creating, updating, and retrieving users.
 * All actions are restricted to managers.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "Controller for getting, updating users"
)
public class UserController {
    private final UserService userService;

    /**
     * Constructs the UserController with the required user service.
     *
     * @param userService the service handling user logic
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param userRequestDTO the user data to create
     * @return the created user
     */
    @PostMapping
    @Operation(
            summary = "Creating user"
    )
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.create(userRequestDTO)), HttpStatus.CREATED);
    }

    /**
     * Updates the username and email of the currently authenticated user.
     *
     * @param username the new username
     * @param email the new email
     * @param user the currently authenticated user
     * @return the updated user
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Updating user"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUser(@RequestParam String username,
                                                      @RequestParam String email,
                                                      @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.update(user, username, email)), HttpStatus.OK);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the given ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Getting user by id"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.get(id)), HttpStatus.OK);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return all users in the system
     */
    @GetMapping
    @Operation(
            summary = "Getting all users"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> getAllUsers() {
        return new ResponseEntity<>(new ResponseWrapper<>(userService.getAll()), HttpStatus.OK);
    }

}
