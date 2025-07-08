package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.entity.PersonalInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link PersonalInfo} entities and their corresponding DTOs.
 *
 * <p>This mapper enriches the response by including related {@link de.university.staffmanagement.entity.User} data.
 */
@Mapper(componentModel = "spring")
public interface PersonalInfoMapper {

    /**
     * Converts a {@link PersonalInfo} entity to a {@link PersonalInfoResponseDTO}.
     *
     * <p>Includes additional user-related information like ID, email, username, and role.
     *
     * @param personalInfo the personal info entity
     * @return the mapped PersonalInfoResponseDTO
     */
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.role", target = "role")
    PersonalInfoResponseDTO toDTO(PersonalInfo personalInfo);
    /**
     * Converts a {@link PersonalInfoRequestDTO} to a {@link PersonalInfo} entity.
     *
     * @param personalInfoRequestDTO the request DTO with updated personal details
     * @return the mapped PersonalInfo entity
     */
    PersonalInfo toEntity (PersonalInfoRequestDTO personalInfoRequestDTO);
}
