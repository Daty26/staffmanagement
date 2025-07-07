package de.university.staffmanagement.dto.request;


import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for submitting or updating a user's personal information.
 *
 * <p>Includes name, contact details, role, birth date, and associated username.
 */
@Data
public class PersonalInfoRequestDTO {

    private String fullName;
    private String email;
    private Role role;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String username;
//    private Long userId;
}
