package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.User;

import java.util.List;

public interface ClockService {
    ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO, User authenticatedUser);
    ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO, User authenticatedUser);
    List<ClockResponseDTO> getEntryByUser(User user);
    List<ClockResponseDTO> getAll();
}
