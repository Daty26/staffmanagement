package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.entity.User;

import java.util.List;

/**
 * Service interface for managing user personal information.
 *
 * <p>This service provides functionality to:
 * <ul>
 *     <li>Retrieve the personal information of a specific user</li>
 *     <li>Update or create a user's personal profile</li>
 *     <li>Fetch all personal information records in the system</li>
 * </ul>
 */
public interface PersonalInfoService {
    /**
     * Retrieves the personal information for the specified user.
     *
     * @param user the user whose personal information is requested
     * @return the user's personal information as a response DTO
     */
    PersonalInfoResponseDTO get(User user);

    /**
     * Updates the personal information of the given user.
     * If no profile exists yet, a new one is created.
     *
     * @param personalInfoRequestDTO contains the new personal information (e.g. name, address)
     * @param user the authenticated user whose information should be updated
     * @return the updated personal information as a response DTO
     */
    PersonalInfoResponseDTO update(PersonalInfoRequestDTO personalInfoRequestDTO, User user);

    /**
     * Retrieves all personal information records for all users in the system.
     *
     * @return a list of all user personal info as response DTOs
     */
    List<PersonalInfoResponseDTO> getAll();
}
