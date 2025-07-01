package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.LeaveType;
import de.university.staffmanagement.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    //Maybe create enums for leave_type?
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = true)
    private String managerComment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId",nullable = false)
    private User user;

}