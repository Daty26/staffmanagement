package de.university.staffmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a clock-in/clock-out record for a user.
 *
 * <p>Each entry contains a start time, an optional end time,
 * and a reference to the associated user.
 */
@Entity
@Setter
@Getter
public class ClockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    @Column(nullable = false)
    private LocalDateTime clockInTime;

    // Can be null if the user hasn't clocked out yet
    private LocalDateTime clockOutTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}