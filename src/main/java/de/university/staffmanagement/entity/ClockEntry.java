package de.university.staffmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ClockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    @Column(nullable = false)
    private LocalDateTime clockInTime;

    private LocalDateTime clockOutTime; // Can be null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}