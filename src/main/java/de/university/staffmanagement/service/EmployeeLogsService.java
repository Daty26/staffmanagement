package de.university.staffmanagement.service;


import de.university.staffmanagement.dto.request.EmployeeRequestDTO;
import de.university.staffmanagement.dto.response.EmployeeResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeLogsService {


    List<EmployeeResponseDTO> getWeeklyOverview(Long userId, LocalDate weekStart);
}
