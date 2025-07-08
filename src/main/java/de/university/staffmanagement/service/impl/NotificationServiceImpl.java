package de.university.staffmanagement.service.impl;


import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.entity.Notification;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.NotificationMapper;
import de.university.staffmanagement.mapper.UserMapper;
import de.university.staffmanagement.repository.NotificationRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link de.university.staffmanagement.service.NotificationService} interface.
 *
 * <p>This service provides functionality for sending, retrieving, and deleting notifications for users.
 *
 * <p>Main functionalities include:
 * <ul>
 *     <li>Sending a notification to a user by username or DTO</li>
 *     <li>Fetching all system notifications</li>
 *     <li>Fetching notifications for a specific user</li>
 *     <li>Deleting a notification by its ID</li>
 * </ul>
 *
 * <p>Uses {@link NotificationRepository} for persistence and {@link NotificationMapper} for mapping
 * between entities and DTOs.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
    }

    /**
     * Sends a notification to a user based on the given DTO and returns the created notification.
     *
     * @param dto contains the recipient username and the message content
     * @return the created notification as a response DTO
     * @throws RuntimeException if the username is not found
     */
    @Override
    public NotificationResponseDTO sendNotification(NotificationRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setUser(user);
        notification.setSentDate(LocalDateTime.now());
        notification.setRead(false);

        notificationRepository.save(notification);
        return notificationMapper.toDTO(notification);
    }

    /**
     * Deletes a notification from the system by its ID.
     *
     * @param id the ID of the notification to delete
     */
    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    /**
     * Sends a notification to a user by username with the given message.
     *
     * @param username the recipient's username
     * @param message the message to be sent
     * @throws RuntimeException if the user with the given username is not found
     */
    @Override
    public void sendNotification(String username, String message) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with the username '" + username + "' not found"));
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setSentDate(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    /**
     * Retrieves all notifications stored in the system.
     *
     * @return a list of all notifications as DTOs
     */
    @Override
    public List<NotificationResponseDTO> getAll() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all notifications for a specific user by user ID.
     *
     * @param userId the ID of the user
     * @return a list of notifications for the user
     * @throws GeneralException if no user is found with the given ID
     */

    @Override
    public List<NotificationResponseDTO> getNotificationByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User with this id was not found "));
        List<Notification> notifications = notificationRepository.findByUser(user);
        return notifications.stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
