package de.university.staffmanagement.entity;


import de.university.staffmanagement.enums.Roles;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToOne(mappedBy = "user") // one user one PersonalInfo
    private PersonalInfo personalInfo;

    @OneToMany(mappedBy = "user") //one user - several entries
    private List<ClockEntry> clockEntries;

    @OneToMany(mappedBy = "user") // one user -several requests
    private List<LeaveRequest> leaveRequests;

}
