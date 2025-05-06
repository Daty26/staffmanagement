package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO userRequestDTO);

    UserResponseDTO update(User user, String username, String email);

    UserResponseDTO get(Long id);

    List<UserResponseDTO> getAll();
}
