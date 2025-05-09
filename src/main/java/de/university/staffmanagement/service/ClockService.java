package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;

public interface ClockService {
    ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO);
    ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO);
}
