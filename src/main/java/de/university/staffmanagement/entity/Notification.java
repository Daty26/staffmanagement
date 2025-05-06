package de.university.staffmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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