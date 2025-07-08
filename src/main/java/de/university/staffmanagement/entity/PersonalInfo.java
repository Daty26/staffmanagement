package de.university.staffmanagement.entity;
import de.university.staffmanagement.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity representing additional personal information for a user.
 *
 * <p>Includes full name, contact details, birth date, and a reference to the associated user.
 */
@Entity
@Getter
@Setter
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;

//    @Column(unique = true, nullable = false)
//    private String email;

    @Column(nullable = false)
    private String fullName;

    private String phoneNumber;

    private String address;

    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}