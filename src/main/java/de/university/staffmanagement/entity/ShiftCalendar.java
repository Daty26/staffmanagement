package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.ScheduleType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "shift_calendar")
public class ShiftCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long calendarId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "start_date") //added
    private LocalDate startDate;

    @Column(name = "end_date") // added
    private LocalDate endDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;
}
