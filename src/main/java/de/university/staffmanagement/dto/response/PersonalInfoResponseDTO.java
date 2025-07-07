package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Role;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO representing a user's personal information.
 *
 * <p>Includes contact details, role, and account identifiers.
 * If the user has no personal info stored yet, default values are used.
 */
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

    /**
     * Constructs a default personal info response from a {@link User} entity
     * when no personal data exists yet.
     *
     * @param user the user entity
     */
    public PersonalInfoResponseDTO(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.fullName = "";
        this.phoneNumber = "";
        this.address = "";
        this.birthDate = null;
    }
}
