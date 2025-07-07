package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO representing a notification sent to a user.
 *
 * <p>Includes the notification message, timestamp, read status, and recipient username.
 */
@Data
public class NotificationResponseDTO {
    private Long notificationId;
    private String message;
    private LocalDateTime sentDate;
    private boolean read;
    @NotNull
    private String username;
}
