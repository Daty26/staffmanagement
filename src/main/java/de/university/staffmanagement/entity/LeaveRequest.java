package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    //Maybe create enums for leave_type?
    @Column(nullable = false)
    private String leaveType;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = true) //for test
//    private User user;

}