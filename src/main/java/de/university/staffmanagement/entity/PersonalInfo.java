package de.university.staffmanagement.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @Column(nullable = false)
    private String fullName;

    private String phoneNumber;

    private String address;

    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}