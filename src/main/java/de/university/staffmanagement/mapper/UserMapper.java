package de.university.staffmanagement.mapper;


import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);
    User toEntity(UserRequestDTO userRequestDTO);
}
