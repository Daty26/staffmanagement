package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.enums.Role;
import lombok.Data;

/**
 * DTO representing a user in the system.
 *
 * <p>Includes user ID, username, password (hashed), role, and associated personal information.
 */
@Data
public class UserResponseDTO {
    private Long userId;
    private String username;
    private String password;
    private Role role;
    private PersonalInfo personalInfo;
}
