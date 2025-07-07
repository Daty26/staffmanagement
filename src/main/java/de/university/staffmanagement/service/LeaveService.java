package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.LeaveStatusUpdateDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;

import java.util.List;

/**
 * Service interface for managing employee leave requests.
 *
 * <p>This service provides functionality to:
 * <ul>
 *     <li>Create a new leave request</li>
 *     <li>Retrieve all leave requests or filter them by user or status</li>
 *     <li>Update the approval status of a request</li>
 *     <li>Fetch individual request details</li>
 * </ul>
 */
public interface LeaveService {
    /**
     * Creates a new leave request for the given user.
     *
     * @param leaveRequestDTO the request data including leave type, dates, and reason
     * @param user the user submitting the leave request
     * @return the created leave request as a response DTO
     */
    LeaveResponseDTO create(LeaveRequestDTO leaveRequestDTO, User user);
    /**
     * Retrieves all leave requests in the system.
     *
     * @return a list of all leave requests as response DTOs
     */
    List<LeaveResponseDTO> getAll();
    /**
     * Retrieves a specific leave request by its ID.
     *
     * @param id the ID of the leave request
     * @return the leave request as a response DTO
     */
    LeaveResponseDTO getReqById(Long id);
    /**
     * Retrieves leave requests for a specific user filtered by status.
     *
     * @param status the leave request status to filter by (e.g. PENDING, APPROVED)
     * @param userId the ID of the user
     * @return a list of matching leave requests as response DTOs
     */
    List<LeaveResponseDTO> getByStatus(Status status, Long userId);
    /**
     * Updates the status of a leave request (e.g., approves or rejects it).
     *
     * @param id the ID of the leave request to update
     * @param leaveStatusUpdateDTO contains the new status and optional manager comment
     * @return the updated leave request as a response DTO
     */
    LeaveResponseDTO updateStatus(Long id, LeaveStatusUpdateDTO leaveStatusUpdateDTO);
    /**
     * Retrieves all leave requests submitted by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of the user's leave requests as response DTOs
     */
    List<LeaveResponseDTO> getReqByUserId(Long userId);

}
