package de.university.staffmanagement.mapper;


import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link User} entities and their corresponding DTOs.
 *
 * <p>Used to translate user data between the persistence layer and the API layer.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserResponseDTO}.
     *
     * @param user the user entity
     * @return the response DTO containing user information
     */
    UserResponseDTO toDTO(User user);
    /**
     * Converts a {@link UserRequestDTO} to a {@link User} entity.
     *
     * @param userRequestDTO the request DTO containing new user details
     * @return the corresponding User entity
     */
    User toEntity(UserRequestDTO userRequestDTO);
}
