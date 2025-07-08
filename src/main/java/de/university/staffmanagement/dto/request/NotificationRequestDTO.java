package de.university.staffmanagement.dto.request;

import lombok.Data;


/**
 * DTO for sending a notification to a specific user.
 *
 * <p>Contains the message content and the recipient's username.
 */
@Data
public class NotificationRequestDTO {

    private String message;
    private String username;
}
