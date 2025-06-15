package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.entity.User;
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

    //create new personal infor if user has none
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
