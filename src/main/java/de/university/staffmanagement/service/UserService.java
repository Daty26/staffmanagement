package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;

import java.util.List;

/**
 * Service interface for managing user accounts within the staff management system.
 *
 * <p>This service provides functionality to:
 * <ul>
 *     <li>Create new user accounts with encoded passwords and assigned roles</li>
 *     <li>Update user credentials (e.g. username, email)</li>
 *     <li>Retrieve individual user details</li>
 *     <li>List all users in the system</li>
 * </ul>
 */
public interface UserService {
    /**
     * Creates a new user based on the provided registration details.
     *
     * @param userRequestDTO contains user credentials and optional role
     * @return the created user as a response DTO
     */
    UserResponseDTO create(UserRequestDTO userRequestDTO);

    /**
     * Updates the username and email of an existing user.
     *
     * @param user the existing user entity to be updated
     * @param username the new username to set
     * @param email the new email to set
     * @return the updated user as a response DTO
     */
    UserResponseDTO update(User user, String username, String email);

    /**
     * Retrieves user details by ID.
     *
     * @param id the ID of the user
     * @return the user's information as a response DTO
     */
    UserResponseDTO get(Long id);

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users as response DTOs
     */
    List<UserResponseDTO> getAll();
}
