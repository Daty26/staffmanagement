package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;

import java.util.List;

/**
 * Service interface for handling user notifications in the staff management system.
 *
 * <p>This service provides functionality to:
 * <ul>
 *     <li>Send notifications to users (via DTO or directly by username)</li>
 *     <li>Delete existing notifications</li>
 *     <li>Retrieve all notifications or those specific to a user</li>
 * </ul>
 */
public interface NotificationService {
    /**
     * Sends a notification to a user using a DTO containing the username and message.
     *
     * @param notificationRequestDTO contains the recipient username and message
     * @return the created notification as a response DTO
     */
    NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO);
    /**
     * Deletes a notification by its ID.
     *
     * @param id the ID of the notification to be deleted
     */
    void deleteNotification(Long id);
    /**
     * Sends a notification to a user identified by username with the given message.
     *
     * @param username the recipient's username
     * @param message the content of the notification
     */
    void sendNotification(String username, String message);
    /**
     * Retrieves all notifications in the system.
     *
     * @return a list of all notifications as response DTOs
     */
    List<NotificationResponseDTO> getAll();
    /**
     * Retrieves all notifications for a specific user by their user ID.
     *
     * @param userId the ID of the user
     * @return a list of the user's notifications as response DTOs
     */
    List<NotificationResponseDTO> getNotificationByUserId(Long userId);
}
