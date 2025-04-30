package de.university.staffmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "personal_info")
public class PersonalInfo {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    private LocalDate birthdate;
    private String email;
    private String address;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
