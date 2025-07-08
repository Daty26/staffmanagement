package de.university.staffmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a notification sent to a user.
 *
 * <p>Each notification includes a message, timestamp, read status, and the associated recipient user.
 */
@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sentDate;

    @Column(nullable = false)
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}