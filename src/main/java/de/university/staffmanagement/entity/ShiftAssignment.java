package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.ScheduleType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity representing a scheduled shift assigned to a user.
 *
 * <p>Includes the shift date, time range, schedule type, and the associated employee.
 */
@Entity
@Data
public class ShiftAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;

    @Column(nullable = false)
    private LocalDate shiftDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleType shiftType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}