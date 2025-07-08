package de.university.staffmanagement.controller;


import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user notifications.
 *
 * <p>Provides endpoints for sending, retrieving, and deleting system notifications.
 * Access is controlled by user role via Spring Security.
 */
@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Controller for managing notifications")

public class NotificationController {

    private final NotificationService notificationService;
    /**
     * Constructs the controller with the required notification service.
     *
     * @param notificationService the service handling notification logic
     */
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    /**
     * Sends a new notification.
     * Accessible only to users with the MANAGER role.
     *
     * @param notificationRequestDTO the notification details to send
     * @return the created notification wrapped in a response object
     */
    @Operation(
            summary = "Send notification",
            description = "Create and send a new notification"
    )
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<NotificationResponseDTO>> sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        return new ResponseEntity<>(new ResponseWrapper<>(notificationService.sendNotification(notificationRequestDTO)), HttpStatus.CREATED);
    }
    /**
     * Deletes a notification by its ID.
     * Only available to users with the MANAGER role.
     *
     * @param id the ID of the notification to delete
     * @return a void response with success status
     */
    @Operation(
            summary = "Delete notification",
            description = "Delete a notification by its ID"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok(new ResponseWrapper<>(null, ""));
    }
    /**
     * Retrieves notifications sent to the authenticated user.
     *
     * @param user the currently authenticated user
     * @return a list of the user's notifications
     */
    @Operation(
            summary = "Get user notifications",
            description = "Retrieve notifications for the authenticated user"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ResponseWrapper<List<NotificationResponseDTO>>> getNotificationsByUserId(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>( notificationService.getNotificationByUserId(user.getUserId())), HttpStatus.OK);
    }
    /**
     * Retrieves all notifications in the system.
     *
     * @return a list of all notifications
     */
    @Operation(
            summary = "Get all notifications",
            description = "Retrieve all notifications in the system")
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ResponseWrapper<List<NotificationResponseDTO>>> getAllNotifications() {
        List<NotificationResponseDTO> notifications = notificationService.getAll();
        return new ResponseEntity<>(new ResponseWrapper<>(notifications), HttpStatus.OK);
    }
}
