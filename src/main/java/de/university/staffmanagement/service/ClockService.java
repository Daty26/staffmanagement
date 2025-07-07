package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.User;

import java.util.List;

/**
 * Service interface for managing clock-in and clock-out operations for users.
 *
 * <p>This service provides functionalities to:
 * <ul>
 *     <li>Record when a user clocks in or out</li>
 *     <li>Retrieve clock entries by user</li>
 *     <li>Retrieve all clock entries in the system</li>
 * </ul>
 */
public interface ClockService {
    /**
     * Records a clock-in entry for the authenticated user.
     *
     * @param clockInRequestDTO contains optional remarks or metadata for clock-in
     * @param authenticatedUser the currently authenticated user performing the clock-in
     * @return the created clock-in entry as a response DTO
     */
    ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO, User authenticatedUser);
    /**
     * Records a clock-out entry for the authenticated user and finalizes the current clock session.
     *
     * @param clockOutRequestDTO contains optional remarks or metadata for clock-out
     * @param authenticatedUser the currently authenticated user performing the clock-out
     * @return the updated clock entry including clock-in and clock-out timestamps
     */

    ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO, User authenticatedUser);
    /**
     * Retrieves all clock entries associated with a specific user.
     *
     * @param user the user whose clock entries should be fetched
     * @return a list of clock entry DTOs for the specified user
     */
    List<ClockResponseDTO> getEntryByUser(User user);
    /**
     * Retrieves all clock entries in the system.
     *
     * @return a list of all clock entry DTOs
     */
    List<ClockResponseDTO> getAll();
}
