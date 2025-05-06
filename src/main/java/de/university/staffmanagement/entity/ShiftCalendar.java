package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.ScheduleType;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ShiftCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;

    @Column(nullable = false)
    private LocalDate shiftDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleType shiftType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}