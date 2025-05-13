package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.enums.Role;
import lombok.Data;
import java.time.LocalDate;


@Data
public class PersonalInfoResponseDTO {

    private String fullName;
    private String email;
    private Role role;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String username;
    private Long userId;
}
