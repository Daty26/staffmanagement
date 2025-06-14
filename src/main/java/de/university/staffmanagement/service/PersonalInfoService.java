package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.entity.User;

import java.util.List;

public interface PersonalInfoService {
    PersonalInfoResponseDTO get(Long id);

    PersonalInfoResponseDTO update(PersonalInfoRequestDTO personalInfoRequestDTO);

    List<PersonalInfoResponseDTO> getAll();
}
