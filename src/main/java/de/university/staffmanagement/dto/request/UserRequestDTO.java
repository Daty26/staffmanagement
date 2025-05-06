package de.university.staffmanagement.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import de.university.staffmanagement.enums.Role;


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
