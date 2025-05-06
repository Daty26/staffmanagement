package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.enums.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;
    private String username;
    private String password;
    private Role role;
    private PersonalInfo personalInfo;
}
