package de.university.staffmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "clock_entries")
public class ClockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long entryId;

    @Column(name = "clock_in_time")
    private LocalDate clockInTime;

    @Column(name = "clock_out_time")
    private LocalDate clockOutTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
