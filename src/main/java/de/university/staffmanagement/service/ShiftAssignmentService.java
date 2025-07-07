package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;

import java.util.List;

/**
 * Service interface for managing shift assignments of users.
 *
 * <p>This service provides functionality to:
 * <ul>
 *     <li>Create a new shift assignment for a user</li>
 *     <li>Retrieve all shift assignments</li>
 *     <li>Retrieve shift assignments for a specific user</li>
 * </ul>
 */
public interface ShiftAssignmentService {
    /**
     * Creates a new shift assignment based on the provided request data.
     *
     * @param request the shift assignment details (e.g. user ID, date, time range, type)
     * @return the created shift assignment as a response DTO
     */
    ShiftAssignmentResponse create(ShiftAssignmentRequest request);
    /**
     * Retrieves all shift assignments in the system.
     *
     * @return a list of all shift assignments as response DTOs
     */
    List<ShiftAssignmentResponse> getAll();
    /**
     * Retrieves all shift assignments assigned to a specific user.
     *
     * @param userId the ID of the user
     * @return a list of the user's shift assignments as response DTOs
     */
    List<ShiftAssignmentResponse> getByUserId(Long userId);
}
