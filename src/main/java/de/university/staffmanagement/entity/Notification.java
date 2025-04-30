package de.university.staffmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "sent_at")
    private LocalDateTime sentAt; // added
}
