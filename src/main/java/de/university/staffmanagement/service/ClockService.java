package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;

import java.util.List;

public interface ClockService {
    ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO);
    ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO);
    List<ClockResponseDTO> getEntryByUserId(Long userId);
    List<ClockResponseDTO> getAll();
}
