package de.university.staffmanagement.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import de.university.staffmanagement.enums.Role;


/**
 * DTO for creating a new user account.
 *
 * <p>Includes username, password, email, and role.
 * Fields are validated to ensure they are not blank.
 */
@Data
public class UserRequestDTO {

    @NotBlank(message = "Username can not be empty")
    private String username;

    @NotBlank(message = "Password can not be empty")
    private String password;

    @NotBlank(message = "Email can not be empty")
    private String email;

    private Role role;

}
