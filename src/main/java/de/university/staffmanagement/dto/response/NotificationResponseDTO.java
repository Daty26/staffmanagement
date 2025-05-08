package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponseDTO {
    private Long notificationId;
    private String message;
    private LocalDateTime sentDate;
    private boolean read;
    private Long userId;
}
